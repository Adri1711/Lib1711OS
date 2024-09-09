package com.adri1711.util.enums;

import java.util.List;

public class CreateCombinations {

	public static void main(String[] args) {

		for (Material1_8 material1_8 : Material1_8.values()) {
			String mat1_8 = material1_8.toString();
			List<String> mat1_9 = Material1_9.getMaterials(material1_8);
			for (String m1_9 : mat1_9) {
				List<String> mat1_10 = Material1_10.getMaterials(m1_9);
				for (String m1_10 : mat1_10) {
					List<String> mat1_11 = Material1_11.getMaterials(m1_10);
					for (String m1_11 : mat1_11) {
						List<String> mat1_12 = Material1_12.getMaterials(m1_11);
						for (String m1_12 : mat1_12) {
							List<String> mat1_13 = Material1_13.getMaterials(m1_12);
							for (String m1_13 : mat1_13) {
								List<String> mat1_14 = Material1_14.getMaterials(m1_13);
								for (String m1_14 : mat1_14) {
									List<String> mat1_15 = Material1_15.getMaterials(m1_14);
									for (String m1_15 : mat1_15) {
										List<String> mat1_16 = Material1_16.getMaterials(m1_15);
										

										for (String m1_16 : mat1_16) {
											
											List<String> mat1_16_2 = Material1_16.getMaterials(m1_16);
											

											for (String m1_16_2 : mat1_16_2) {
											System.out.println(mat1_8+ "(\""+mat1_8 + "\",\"" + m1_9 + "\",\"" + m1_10 + "\",\"" + m1_11 + "\",\""
													+ m1_12 + "\",\"" + m1_13 + "\",\"" + m1_14 + "\",\"" + m1_15 + "\",\"" + m1_16+ "\",\"" + m1_16_2+"\"),\n");
											}
										}
									}
								}
							}
						}
					}
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
