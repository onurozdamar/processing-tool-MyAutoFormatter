package template.tool;

import java.util.ArrayList;
import java.util.regex.Pattern;

class Formatter {

	private String[] operators = { "==", "!=", "&&", "||", "<=", ">=", "+=", "-=", "*=", "/=", "%=", "+", "-", "*", "/",
			"%", "=", ":", "?", "<", ">", "|", "&" };

	private String[] lines;

	Formatter() {
	}

	public void format(String text) {
		lines = splitToLine(text);

		for (int i = 0; i < lines.length; i++) {
			ArrayList<Operator> operatorsInLine = getOperatorsInLine(lines[i]);
			for (Operator operator : operatorsInLine) {
				lines[i] = operator.addSpace(lines[i]);
			}
		}
	}

	private String[] splitToLine(String text) {
		return text.split("\n");
	}

	private String createReplaceString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append("s");
		}
		return sb.toString();
	}

	private ArrayList<Operator> getOperatorsInLine(String line) {
		ArrayList<Operator> operatorsInLine = new ArrayList<Operator>();
		line = replace(line);

		for (int i = 0; i < operators.length; i++) {
			if (line.contains(operators[i])) {
				Operator o = new Operator(operators[i]);
				o.setIndex(line.indexOf(operators[i]));
				operatorsInLine.add(o);
				line = o.addSpace(line);
				line = line.replaceFirst(Pattern.quote(operators[i]), createReplaceString(operators[i].length()));
				i--;
			}
		}
		return operatorsInLine;
	}

	private String replace(String line) {
		line = replaceFixedOperators(line);

		if (line.contains("\"")) {
			line = replaceStrings(line);
		}
		if (line.contains("//")) {
			line = replaceSingleLineComment(line);
		}
		if (line.contains("'")) {
			line = replaceChars(line);
		}

		return line;
	}

	private String replaceFixedOperators(String line) {
		String[] operatorsDontSpace = { "--", "++", ".*", "/*", "*/" };
		for (int i = 0; i < operatorsDontSpace.length; i++) {
			if (line.contains(operatorsDontSpace[i])) { // dont want to add space these
				line = line.replace(operatorsDontSpace[i], createReplaceString(operatorsDontSpace[i].length()));
			}
		}
		return line;
	}

	private String replaceSingleLineComment(String line) {
		StringBuilder sb = new StringBuilder();
		char[] chars = line.toCharArray();
		int i = line.indexOf("//") - 1;
		while (i + 1 < chars.length) {
			sb.append(chars[i + 1]);
			i++;
		}
		line = line.replace(sb.toString(), createReplaceString(sb.length()));
		return line;
	}

	private String replaceStrings(String line) {// e.g. "this is string"
		char[] chars = line.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '"') {
				StringBuilder sb = new StringBuilder();
				do {
					sb.append(chars[i]);
					i++;
				} while (i + 1 < chars.length && chars[i + 1] != '"');
				sb.append(chars[i]);
				if (i + 1 < chars.length) {
					sb.append(chars[++i]);
				}
				line = line.replace(sb.toString(), createReplaceString(sb.length()));
				i++;
			}
		}
		return line;
	}

	private String replaceChars(String line) { // e.g. 'a'
		char[] chars = line.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '\'') {
				StringBuilder sb = new StringBuilder();

				sb.append(chars[i]);
				sb.append(chars[i + 1]);
				sb.append(chars[i + 2]);

				line = line.replace(sb.toString(), createReplaceString(3));
				i += 3;
			}
		}
		return line;
	}

	public String getText() {
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line + "\n");
		}
		return sb.toString();
	}
}
