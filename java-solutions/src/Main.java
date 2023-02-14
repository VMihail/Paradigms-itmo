public class Main {
  public static void main(String[] args) {
    // 19 jdk?
    String code = """
              function hi() {
                  console.log('"Hello, world!"');
              }
                              
              hi();
              """;
    System.out.println(code);
  }
}
