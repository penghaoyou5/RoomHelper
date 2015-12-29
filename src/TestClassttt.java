

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public class TestClassttt implements Serializable {

	public String SuccessMsg;
	public int ErrorMsg;
	public List<ListMy> list;

	public class ListMy implements Serializable {

		public String PropertTypeName;
		public String PreBuildingName;
		public String ActHouseName;
		public String PreHouseName;
		public int CheckRound;
		public String HouseCode;
		public String SaleRecordCode;
		public String PreHouseFullName;
		public String PreUnitName;
		public String BuildingCode;
		public String ActUnitName;
		public String ActHouseFullName;
		public String UnitCode;
		public int CheckStauts;
		public String OwnerNames;
		public String ActBuildingName;
		public List<LastCheckProblemListMy> LastCheckProblemList;

		public class LastCheckProblemListMy implements Serializable {

			public String SpaceLayoutCode;
			public String PreBuildingName;
			public String ActHouseName;
			public String PreHouseName;
			public String CheckRound;
			public String TaskCode;
			public String HouseCode;
			public String SpaceLayoutName;
			public String SpaceLayoutFullName;
			public String PreHouseFullName;
			public String PreUnitName;
			public String PreCheckCode;
			public String BuildingCode;
			public String ActUnitName;
			public String ActHouseFullName;
			public String UnitCode;
			public String CheckStauts;
			public String ProPretTypeName;
			public String OwnerNames;
			public String[] AttachmentIDS;

			@Override
			public String toString() {
				String s = "";
				Field[] arr = this.getClass().getFields();
				for (Field f : getClass().getFields()) {
					try {
						s += f.getName() + "=" + f.get(this) + "\n,";
					} catch (Exception e) {
					}
				}
				return getClass().getSimpleName()
						+ "["
						+ (arr.length == 0 ? s : s.substring(0, s.length() - 1))
						+ "]";
			}
		}

		public List<SpaceLayoutListMy> SpaceLayoutList;

		public class SpaceLayoutListMy implements Serializable {
			public String[] AttachmentIDS;

			public String SpaceLayoutFullName;
			public int SpaceLayoutCode;
			public String SpaceLayoutName;
			public List<EnginTypeListMy> EnginTypeList;

			public class EnginTypeListMy implements Serializable {

				public String EnginTypeFullName;
				public String CheckRemark;
				public String EnginTypeName;
				public String EnginTypeCode;
				public String[] AttachmentIDS;
				public List<ProblemDescriptionListMy> ProblemDescriptionList;

				public class ProblemDescriptionListMy implements Serializable {

					public String ProblemDescriptionName;
					public String ProblemDescriptionCode;

					@Override
					public String toString() {
						String s = "";
						Field[] arr = this.getClass().getFields();
						for (Field f : getClass().getFields()) {
							try {
								s += f.getName() + "=" + f.get(this) + "\n,";
							} catch (Exception e) {
							}
						}
						return getClass().getSimpleName()
								+ "["
								+ (arr.length == 0 ? s : s.substring(0,
										s.length() - 1)) + "]";
					}
				}

				@Override
				public String toString() {
					String s = "";
					Field[] arr = this.getClass().getFields();
					for (Field f : getClass().getFields()) {
						try {
							s += f.getName() + "=" + f.get(this) + "\n,";
						} catch (Exception e) {
						}
					}
					return getClass().getSimpleName()
							+ "["
							+ (arr.length == 0 ? s : s.substring(0,
									s.length() - 1)) + "]";
				}
			}

			@Override
			public String toString() {
				String s = "";
				Field[] arr = this.getClass().getFields();
				for (Field f : getClass().getFields()) {
					try {
						s += f.getName() + "=" + f.get(this) + "\n,";
					} catch (Exception e) {
					}
				}
				return getClass().getSimpleName()
						+ "["
						+ (arr.length == 0 ? s : s.substring(0, s.length() - 1))
						+ "]";
			}
		}

		@Override
		public String toString() {
			String s = "";
			Field[] arr = this.getClass().getFields();
			for (Field f : getClass().getFields()) {
				try {
					s += f.getName() + "=" + f.get(this) + "\n,";
				} catch (Exception e) {
				}
			}
			return getClass().getSimpleName() + "["
					+ (arr.length == 0 ? s : s.substring(0, s.length() - 1))
					+ "]";
		}
	}

	@Override
	public String toString() {
		String s = "";
		Field[] arr = this.getClass().getFields();
		for (Field f : getClass().getFields()) {
			try {
				s += f.getName() + "=" + f.get(this) + "\n,";
			} catch (Exception e) {
			}
		}
		return getClass().getSimpleName() + "["
				+ (arr.length == 0 ? s : s.substring(0, s.length() - 1)) + "]";
	}
}
