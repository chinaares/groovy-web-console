package gwc.service;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@Service
public class GroovyExecutorService {
  public String execute(String scriptContent) throws Exception {
    Binding binding = new Binding();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(out);
    binding.setProperty("out", ps);
    GroovyShell shell = new GroovyShell(binding);
    try {
      shell.evaluate(scriptContent);
      return out.toString();
    } finally {
      ps.close();
    }
  }
}
