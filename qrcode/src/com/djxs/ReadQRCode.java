package com.djxs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;

public class ReadQRCode {

	
	public static void main(String[] args) {
		File file=new File("D:/logo.pngs");
		BufferedImage bufferedImage=null;
		try {
			bufferedImage=ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QRCodeDecoder qrCodeDecoder=new QRCodeDecoder();
		byte[] decode= qrCodeDecoder.decode(new MyQRCodeImage(bufferedImage));
		String result=new String(decode);
		System.out.println("½âÂë£º"+result);

	}

}
