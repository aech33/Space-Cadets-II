import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.lang.Exception;

public class BareBonesInterpreter {
  static String[] commands;
  static HashMap<String, Integer> vars = new HashMap<>();
  static int i;
  public static void main (String[] args)
      throws Exception {
    StringBuilder result = new StringBuilder();
    try (BufferedReader br
             = new BufferedReader(new FileReader(args.length != 0 ? args[0] : "src/testprogram.txt"))) {
      String line;
      while ((line = br.readLine()) != null) {
        result.append(line.trim());
      }
    }
    commands = result.toString().split(";");
    for (i = 0; i < commands.length; i++) {
      interpret();
    }
    vars.forEach((k,v)-> System.out.println(k+" = "+v));
  }
  public static void interpret() {
    String[] commandArgs = commands[i].split(" ");
    switch (commandArgs[0]) {
      case "clear":
        vars.put(commandArgs[1], 0);
        break;
      case "incr":
        vars.put(commandArgs[1], vars.get(commandArgs[1]) + 1);
        break;
      case "decr":
        int currentValue = vars.get(commandArgs[1]);
        if (currentValue > 0) {
          vars.put(commandArgs[1], currentValue - 1);
        }
        break;
      case "while":
        int j = i;
        do {
          i = j + 1;
          while(!commands[i].equals("end")) {
            interpret();
            i += 1;
          }
        } while (vars.get(commandArgs[1]) != 0);
        break;
      case "end":
        break;
    }
  }
}
