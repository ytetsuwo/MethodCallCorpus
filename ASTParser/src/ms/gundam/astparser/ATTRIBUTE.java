package ms.gundam.astparser;

public enum ATTRIBUTE {

	IFNULLCHECK {
		@Override
		public String toString() {
			return "IFNULLCHECK";
		}
	},

	IFTHROW {
		@Override
		public String toString() {
			return "IFTHROW";
		}
	},

	IFRETURN {
		@Override
		public String toString() {
			return "IFRETURN";
		}
	},

	IFVARIABLE {
		@Override
		public String toString() {
			return "IFVARIABLE";
		}
	},

	NORMAL {
		@Override
		public String toString() {
			return "NORMAL";
		}
	};

	abstract public String toString();
}
