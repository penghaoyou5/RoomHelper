package com.sinooceanland.roomhelper.dao.base;

/**
 * @author peng
 * 房屋信息的基类
 */
public abstract class HouseMessageBaseBean extends BaseBean {
	public String PreBuildingName;
	public String ActHouseName;
	public String PreHouseName;
	//int
	public String CheckRound;
	public String HouseCode;
	
	public String PreHouseFullName;
	public String PreUnitName;
	public String BuildingCode;
	public String ActUnitName;
	public String ActHouseFullName;
	public String UnitCode;
	//int
	public String CheckStauts;
	public String OwnerNames;
	public String getPreBuildingName() {
		return PreBuildingName;
	}
	public void setPreBuildingName(String preBuildingName) {
		PreBuildingName = preBuildingName;
	}
	public String getActHouseName() {
		return ActHouseName;
	}
	public void setActHouseName(String actHouseName) {
		ActHouseName = actHouseName;
	}
	public String getPreHouseName() {
		return PreHouseName;
	}
	public void setPreHouseName(String preHouseName) {
		PreHouseName = preHouseName;
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
		return PreHouseFullName;
	}
	public void setPreHouseFullName(String preHouseFullName) {
		PreHouseFullName = preHouseFullName;
	}
	public String getPreUnitName() {
		return PreUnitName;
	}
	public void setPreUnitName(String preUnitName) {
		PreUnitName = preUnitName;
	}
	public String getBuildingCode() {
		return BuildingCode;
	}
	public void setBuildingCode(String buildingCode) {
		BuildingCode = buildingCode;
	}
	public String getActUnitName() {
		return ActUnitName;
	}
	public void setActUnitName(String actUnitName) {
		ActUnitName = actUnitName;
	}
	public String getActHouseFullName() {
		return ActHouseFullName;
	}
	public void setActHouseFullName(String actHouseFullName) {
		ActHouseFullName = actHouseFullName;
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
