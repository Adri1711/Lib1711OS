package com.adri1711.util.enums;

import org.bukkit.Material;

public class CombiRunner {
	public static void main(String[] args) {

		for(Material m:Material.values()){
			System.out.println(m +",\n");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
