package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<String> questions;
    public static ArrayList<String> answers;

    public static void readQuestions() throws FileNotFoundException {
        File questionFile = new File("C:\\regitraQuestions\\src\\sample\\questions.txt");
        Scanner reader = new Scanner(questionFile);
        questions = new ArrayList<>();
        while (reader.hasNextLine()) {
            questions.add(reader.nextLine());
        }
        reader.close();
    }

    public static void getData() throws IOException {
        Document doc = Jsoup.connect("https://www.ketbilietai.lt/egzaminai-regitroje/klausimai-pries-praktikos-egzamina-regitroje/#B-kategorija").get();
        answers = new ArrayList<>();
        Element elem = null;

        Elements elements = doc.getElementById("PageTypography").children();

        int i;
        for (i = 0; i < elements.size(); ++i) {
            if (!elements.get(i).select("a[name='B-kategorija']").isEmpty())
                break;
        }
        for (i = i + 1; i < elements.size(); ++i) {
            Element e = elements.get(i);
            if (!e.select("strong").isEmpty()) {
                if (elem != null) {
                    answers.add(String.valueOf(elem));
                    elem = null;
                }
            }
            else {
                if (elem == null)
                    elem = e;
                else
                {
                    if (!e.select("img").isEmpty()) {
                        String link = "https://ketbilietai.lt/" + e.select("img").attr("src");
                        e.select("img").attr("src", link);
                    }
                    elem.appendChild(e);
                }
            }
            if (!e.select("a[name='BE-kategorija']").isEmpty())
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        readQuestions();
        getData();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("questions.ser"));
        ArrayList<Question> qs = new ArrayList<>();
        for (int i = 0; i < questions.size(); ++i) {
            qs.add(new Question(questions.get(i), answers.get(i)));
        }
        out.writeObject(qs);
        out.flush();
        out.close();

    }
}

