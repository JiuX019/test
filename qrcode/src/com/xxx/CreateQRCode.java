package com.xxx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class CreateQRCode {
	static void createqrcode(int version, String content, String imgpath,
			String startRgb, String endRgb) throws IOException {

		Qrcode qrcode = new Qrcode();

		qrcode.setQrcodeVersion(version);

		qrcode.setQrcodeErrorCorrect('M');
		// System.out.println("排错率：" + qrcode.getQrcodeErrorCorrect());
		qrcode.setQrcodeEncodeMode('B');
		// System.out.println("可存储类型：" + qrcode.getQrcodeErrorCorrect());
		int imgSize = 67 + (version - 1) * 12;
		BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gs = bufferedImage.createGraphics();
		qrcode.setQrcodeVersion(version);
		gs.setBackground(Color.WHITE);
		gs.setColor(Color.BLACK);
		gs.clearRect(0, 0, imgSize, imgSize);

		boolean[][] calQrocde = qrcode.calQrcode(content.getBytes());

		int pixoff = 2;
		int startR = 0, startG = 0, startB = 0;
		if (null != startRgb) {
			String[] rgb = startRgb.split(",");
			startR = Integer.valueOf(rgb[0]);
			startG = Integer.valueOf(rgb[1]);
			startB = Integer.valueOf(rgb[2]);
		}
		int endR = 0, endG = 0, endB = 0;
		if (null != endRgb) {
			String[] rgb = endRgb.split(",");
			endR = Integer.valueOf(rgb[0]);
			endG = Integer.valueOf(rgb[1]);
			endB = Integer.valueOf(rgb[2]);
		}
		for (int i = 0; i < calQrocde.length; i++) {

			for (int j = 0; j < calQrocde[i].length; j++) {

				if (calQrocde[i][j]) {

					int r = startR + (endR - startR) * (i + j) / 2
							/ calQrocde.length;
					int g = startG + (endG - startG) * (i + j) / 2
							/ calQrocde.length;
					int b = startB + (endB - startB) * (i + j) / 2
							/ calQrocde.length;

					Color color = new Color(r, g, b);
					gs.setColor(color);
					gs.fillRect(i * 3 + pixoff, j * 3 + pixoff, 3, 3);
				}
			}

		}

		BufferedImage logo = ImageIO.read(new File("D:/logo.jpg"));

		int logoSize = imgSize / 3;

		int o = (imgSize - logoSize) / 2;

		gs.drawImage(logo, o, o, logoSize, logoSize, null);

		gs.dispose();

		bufferedImage.flush();
		try {
			ImageIO.write(bufferedImage, "png", new File(imgpath));
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println("succeed");
	}

	public static void main(String[] args) throws IOException {
		int version = 15;
		String content = "BEGIN:VCARD\r\n"
				+ "FN:姓名:袁庆茹\r\n"
				+ "ORG:公司:xxxxxxx	职位:xxx\r\n"
				+ "TITLE:大二在校生\r\n"
				+ "TEL;WORK;VOICE:17333541022\r\n"
				+ "TEL;HOME;VOICE:17333541022\r\n"
				+ "TEL;CELL;VOICE:17333541022\r\n"
				+ "ADR;WORK:河北科技师范学院\r\n"
				+ "ADR;HOME:河北省衡水市\r\n"
				+ "URL:http://www.NINEPERCENT.com\r\n"
				+ "EMAIL;HOME:470602919@qq.com\r\n"
				+ "PHOTO;VALUE=uri:http://www.NINEPERCENT.com/images/logo.jpg\r\n"
				+ "END:VCARD";

		String imgpath = "D:/q.png";
		String startRgb = "0,255,255";
		String endRgb = "255,0,0";

		createqrcode(version, content, imgpath, startRgb, endRgb);

	}

}
