import java.util.regex.Pattern

databaseChangeLog = {
  changeSet(author: "arcesino", id: "20140916-4") {
    def dropRegex = ~/DROP\s+PROCEDURE.*/
    def createRegex = Pattern.compile(/CREATE\s+PROCEDURE.*\$\$/, Pattern.DOTALL);
    def files = new File('db').listFiles().toList()
    for (file in files) {
      def text = file.text
      def dropSentence = text.find(dropRegex)
      sql(dropSentence)
      def createSentence = text.find(createRegex)[0..-3]
      createProcedure(createSentence)
    }
  }
}
