package template.tool;

public class Operator implements Comparable<Operator> {

	private String operator;
	private char SPACE = ' ';
	private int index;

	Operator(String operator) {
		this.operator = operator;
	}

	public String addSpace(String line) {
		return trimLeft(line, index) + SPACE + operator + SPACE + trimRight(line, index);
	}

	private String trimLeft(String line, int index) {
		char[] chars = line.substring(0, index).toCharArray();

		boolean allSpace = true;

		for (int i = index - 1; i >= 0; i--) {
			if (chars[i] == SPACE) {
				chars[i] = (char) 0;
			} else {
				allSpace = false;
				break;
			}
		}

		return allSpace ? line.substring(0, index - 1) : charArrayToString(chars);
	}

	private String trimRight(String line, int index) {
		char[] chars = line.substring(index + this.operator.length()).toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == ' ') {
				chars[i] = (char) 0;
			} else {
				break;
			}
		}

		return charArrayToString(chars);
	}

	private String charArrayToString(char[] arr) {
		StringBuilder sb = new StringBuilder();
		for (char c : arr) {
			if (c == (char) 0) {
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	public String toString() {
		return operator;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	String getOperator() {
		return operator;
	}

	int getOperatorLength() {
		return operator.length();
	}

	public int compareTo(Operator other) {
		return other.getOperator().length() - this.getOperator().length();
	}
}
