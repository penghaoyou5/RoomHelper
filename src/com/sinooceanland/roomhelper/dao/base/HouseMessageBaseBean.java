package com.sinooceanland.roomhelper.dao.base;

/**
 * @author peng
 * 房屋信息的基类
 */
public abstract class HouseMessageBaseBean extends BaseBean {
	public String ActBuildingName;
	public String PreBuildingName;
	public String PreHouseName;
	public String ActHouseName;
	//int
	public String CheckRound;
	public String HouseCode;
	
	public String ActHouseFullName;
	public String ActUnitName;
	public String BuildingCode;
	public String PreUnitName;
	public String PreHouseFullName;
	public String UnitCode;
	//int
	public String CheckStauts;
	public String OwnerNames;
	public String getPreBuildingName() {
		return ActBuildingName;
	}
	public void setPreBuildingName(String preBuildingName) {
		ActBuildingName = preBuildingName;
	}
	public String getActHouseName() {
		return PreHouseName;
	}
	public void setActHouseName(String actHouseName) {
		PreHouseName = actHouseName;
	}
	public String getPreHouseName() {
		return ActHouseName;
	}
	public void setPreHouseName(String preHouseName) {
		ActHouseName = preHouseName;
	}
	public String getCheckRound() {
		return CheckRound;
	}
	public void setCheckRound(String checkRound) {
		CheckRound = checkRound;
	}
	public String getHouseCode() {
		return HouseCode;
	}
	public void setHouseCode(String houseCode) {
		HouseCode = houseCode;
	}
	public String getPreHouseFullName() {
		return ActHouseFullName;
	}
	public void setPreHouseFullName(String preHouseFullName) {
		ActHouseFullName = preHouseFullName;
	}
	public String getPreUnitName() {
		return ActUnitName;
	}
	public void setPreUnitName(String preUnitName) {
		ActUnitName = preUnitName;
	}
	public String getBuildingCode() {
		return BuildingCode;
	}
	public void setBuildingCode(String buildingCode) {
		BuildingCode = buildingCode;
	}
	public String getActUnitName() {
		return PreUnitName;
	}
	public void setActUnitName(String actUnitName) {
		PreUnitName = actUnitName;
	}
	public String getActHouseFullName() {
		return PreHouseFullName;
	}
	public void setActHouseFullName(String actHouseFullName) {
		PreHouseFullName = actHouseFullName;
	}
	public String getUnitCode() {
		return UnitCode;
	}
	public void setUnitCode(String unitCode) {
		UnitCode = unitCode;
	}
	public String getCheckStauts() {
		return CheckStauts;
	}
	public void setCheckStauts(String checkStauts) {
		CheckStauts = checkStauts;
	}
	public String getOwnerNames() {
		return OwnerNames;
	}
	public void setOwnerNames(String ownerNames) {
		OwnerNames = ownerNames;
	}
}
