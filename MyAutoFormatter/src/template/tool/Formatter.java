package template.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class Formatter {

	public static ArrayList<Operator> operators;
	private String[] lines;

	Formatter() {
		operators = new ArrayList();
		addOperators();
	}

	public void format(String text) {
		lines = splitToLine(text);

		for (int i = 0; i < lines.length; i++) {
			ArrayList<Operator> operatorsInLine = getOperatorsInLine(lines[i]);
			for (Operator operator : operatorsInLine) {
				if (operator == null)
					continue;
				lines[i] = operator.getSpacedText(lines[i], getOperatorCountInLine(lines[i], operator));
			}
		}
	}

	private String[] splitToLine(String text) {
		return text.split("\n");
	}

	private ArrayList<Operator> getOperatorsInLine(String line) {
		ArrayList<Operator> operatorsInLine = new ArrayList();
		line = replace(line);

		for (Operator operator : operators) {
			if (line.contains(operator.getOperator())) {
				operatorsInLine.add(operator);
				line = line.replace(operator.getOperator(), "");
			}
		}
		return operatorsInLine;
	}

	private int getOperatorCountInLine(String line, Operator operator) {
		int operatorCount = 0;

		line = replace(line);

		if (operator.getOperator().length() == 1) { // replace 2 length operators
			for (Operator o : operators) {
				if (o.getOperator().length() == 2) {
					line = line.replace(o.getOperator(), "");
				}
			}
		}

		while (line.contains(operator.toString())) {
			line = line.replaceFirst(Pattern.quote(operator.toString()), "");
			operatorCount++;
		}

		return operatorCount;
	}

	public static String replace(String line) {
		if (line.contains("--")) { // dont want to add space that
			line = line.replace("--", "");
		}
		if (line.contains("++")) {
			line = line.replace("++", "");
		}
		if (line.contains("//")) {
			StringBuilder sb = new StringBuilder();
			char[] chars = line.toCharArray();
			int i = line.indexOf('/') - 1;
			while (i + 1 < chars.length) {
				sb.append(chars[i + 1]);
				i++;
			}
			line = line.replace(sb.toString(), "");
		}
		if (line.contains("/*")) {
			line = line.replace("/*", "");
		}
		if (line.contains("*/")) {
			line = line.replace("*/", "");
		}
		if (line.contains(".*")) {
			line = line.replace(".*", "");
		}

		char[] chars = line.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '"') {
				StringBuilder sb = new StringBuilder();
				while (i + 1 < chars.length && chars[i + 1] != '"') {
					sb.append(chars[i + 1]);
					i++;
				}
				line = line.replace(sb.toString(), "");
				i++;
			} else if (chars[i] == '\'') {
				StringBuilder sb = new StringBuilder();
				while (i + 1 < chars.length && chars[i + 1] != '\'') {
					sb.append(chars[i + 1]);
					i++;
				}
				line = line.replace(sb.toString(), "");
				i++;
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

	private void addOperators() {
		operators.add(new Operator("=="));
		operators.add(new Operator("!="));
		operators.add(new Operator("&&"));
		operators.add(new Operator("||"));
		operators.add(new Operator("<="));
		operators.add(new Operator(">="));
		operators.add(new Operator("+="));
		operators.add(new Operator("-="));
		operators.add(new Operator("*="));
		operators.add(new Operator("/="));
		operators.add(new Operator("%="));

		operators.add(new Operator("+"));
		operators.add(new Operator("-"));
		operators.add(new Operator("*"));
		operators.add(new Operator("/"));
		operators.add(new Operator("%"));
		operators.add(new Operator("="));
		operators.add(new Operator(":"));
		operators.add(new Operator("?"));
		operators.add(new Operator("<"));
		operators.add(new Operator(">"));
		operators.add(new Operator("|"));
		operators.add(new Operator("&"));

		Collections.sort(operators);
	}
}
