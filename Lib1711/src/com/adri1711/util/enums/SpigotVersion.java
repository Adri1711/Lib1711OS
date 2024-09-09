package com.adri1711.util.enums;

public enum SpigotVersion {

	V1_8("v1_8"),

	V1_9_R1("v1_9_R1"),

	V1_9_R2("v1_9_R2"),

	V1_10("v1_10"),

	V1_11("v1_11"),

	V1_12("v1_12"),
	
	V1_13_R1("v1_13_R1"),
	
	V1_13_R2("v1_13_R2"),

	V1_14("v1_14"),
	
	V1_15("v1_15"),

	V1_16("v1_16_R1"),
	
	V1_16_2("v1_16_R2"),

	V1_16_3("v1_16_R3"),

	V1_17_1("v1_17_R1"),
	
	V1_18_1("v1_18_R1"),
	
	V1_18_2("v1_18_R2"),
	
	V1_18_3("v1_18_R3"),

	V1_19_1("v1_19_R1"),
	
	V1_19_2("v1_19_R2"),

	V1_19_3("v1_19_R3"),
	
	V1_20_1("v1_20_R1"),
	
	V1_20_2("v1_20_R2"),
	
	V1_20_3("v1_20_R3"),

	V1_20_4("v1_20_R4"),

	V1_21_1("v1_21_R1");

	private String codigo;

	SpigotVersion(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public static SpigotVersion getValueOf(String version){
		SpigotVersion resultado=null;
		for(SpigotVersion v:values()){
			if(version.contains(v.getCodigo())){
				resultado=v;
			}
		}
		
		return resultado;
	}

}
