import java.io.*;

public class OddEvenFileSplitter implements FileSplitter {


    public static void main(final String[] args) {

        SplitConfig config = new SplitConfig() {
            public String getSourceFilePath() {
                return args[0];
            }

            public String getOddLinesFilePath() {
                return args[1];
            }

            public String getEvenLinesFilePath() {
                return args[2];
            }
        };
        //создаём разделитель, вызываем метод
        OddEvenFileSplitter splitter = new OddEvenFileSplitter();
        splitter.splitFile(config);
    }

    public void splitFile(SplitConfig config) {
        try {
            BufferedReader source = new BufferedReader(new FileReader(config.getSourceFilePath()));//входной файл
            String str;
            int counter = 0;//счётчик строк
            PrintWriter even = new PrintWriter(config.getEvenLinesFilePath());//выходной файл для чётных строк
            PrintWriter odd = new PrintWriter(config.getOddLinesFilePath());//выходной файл нечётных строк
            while ((str = source.readLine()) != null) {
                counter++;
                if (counter % 2 == 1) {
                    odd.println(str);
                } else {
                    even.println(str);
                }
            }
            //закрываем файлы
            even.close();
            odd.close();
            source.close();
        } catch (FileNotFoundException e) {
            System.err.println("Source file doesn't exist");//уведомляем об отсутствии исходного файла
        } catch (IOException e) {
            System.err.println("Unknown IO exception ;)");//документацию насчёт этого не смотрел
        }
    }
}