import java.io.*;
import java.util.*;

public class Problem {
  public static void main(String[] args) {
    try (
      final SimpleFastScanner in = new SimpleFastScanner(System.in);
      final PrintWriter out = new PrintWriter(System.out)
      ) {
      new Solver(in, out).solve();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static class Solver {
    private final SimpleFastScanner in;
    private final PrintWriter out;

    public Solver(SimpleFastScanner in, PrintWriter out) {
      this.in = in;
      this.out = out;
    }

    public void solve() {
      int t = 1;
      // t = in.nextInt();
      for (int i = 0; i < t; i++) {
        solveOne();
      }
    }

    private void solveOne() {

    }
  }


  static public class SimpleFastScanner implements Closeable, Iterator<String> {
    private final BufferedReader reader;
    private StringTokenizer tokenizer;

    public SimpleFastScanner(InputStream inputStream) {
      reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public SimpleFastScanner(File file) throws FileNotFoundException {
      reader = new BufferedReader(new FileReader(file));
    }

    public SimpleFastScanner(String string) {
      reader = new BufferedReader(new StringReader(string));
    }

    @Override
    public void close() throws IOException {
      reader.close();
    }

    @Override
    public boolean hasNext() {
      boolean result = read();
      return result && tokenizer.hasMoreTokens();
    }

    @Override
    public String next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return tokenizer.nextToken();
    }

    public String nextLine() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      StringBuilder result = new StringBuilder();
      while (tokenizer.hasMoreTokens()) {
        result.append(tokenizer.nextToken()).append(" ");
      }
      return result.toString();
    }

    public int nextInt() {
      return Integer.parseInt(next());
    }

    public long nextLong() {
      return Long.parseLong(next());
    }

    public float nextFloat() {
      return Float.parseFloat(next());
    }

    public double nextDouble() {
      return Double.parseDouble(next());
    }

    private boolean read() {
      while (tokenizer == null || !tokenizer.hasMoreTokens()) {
        try {
          String nextLine = reader.readLine();
          if (nextLine == null) {
            return false;
          }
          tokenizer = new StringTokenizer(nextLine);
        } catch (IOException e) {
          System.out.println("I/O error " + e.getMessage());
        }
      }
      return true;
    }
  }
}

