package template.tool;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Operator implements Comparable<Operator> {

	private String operator;
	private static final char SPACE = ' ';

	Operator(String operator) {
		this.operator = operator;
	}

	private String addSpacedText(String line, int index) {
		return trimLeft(line, index) + SPACE + operator + SPACE + trimRight(line, index);
	}

	String getSpacedText(String line, int operatorCount) {
		String temp = line;

		int k = 0;

		for (int i = 0; i < operatorCount; i++) {
			temp = line;
			for (int j = 0; j < i; j++)
				temp = replaceFirst(temp, operator, "");
			int index = temp.indexOf(operator);
			line = addSpacedText(line, index + k);
			k += operator.length();
		}

		return line;
	}

	private String trimLeft(String line, int index) {
		char[] chars = line.substring(0, index).toCharArray();

		for (int i = index - 1; i >= 0; i--) {
			if (chars[i] == SPACE) {
				chars[i] = (char) 0;
			} else {
				break;
			}
		}

		return charArrayToString(chars);
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

	public String toString() {
		return operator;
	}

	public String getOperator() {
		return operator;
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

	private String replaceFirst(String string, String regex, String replace) {

	    if (regex.length() == 2) {
	      string = string.replaceFirst(Pattern.quote(regex), replace);
	      return string;
	    }

	    for (Operator o : Formatter.operators) {
	      if (o.getOperator().length() == 2) {
	        if (string.contains(o.getOperator())) {
	          string = string.replace(o.getOperator(), "aa");
	        }
	      }
	    }

	    return string.replaceFirst(Pattern.quote(regex), replace);
	  }

	public int compareTo(Operator other) {
		return other.getOperator().length() - this.getOperator().length();
	}
}
