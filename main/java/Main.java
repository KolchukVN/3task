import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.ArrayList;

public class Main {

    public static int counter = 1;

    public static void main(String[] args) {

        String str;
        Faker faker = new Faker();
        Map<String, String> l = new HashMap<>();
        l.put("en_Us", "Us");
        l.put("ru_Ru", "Ru");
        l.put("be_By", "Be");

        if (args.length >= 2 && args.length < 4)
        {
            if (isInteger(args[1]))
            {
                for (Map.Entry<String, String> i: l.entrySet())
                {
                    if (args[0].equals(i.getKey()))
                    {
                        if ("Us".equals(i.getValue())) {
                            faker = new Faker(new Locale("en", "Us"));
                        } else if ("Ru".equals(i.getValue())) {
                            faker = new Faker(new Locale("ru", "Ru"));
                        } else if ("Be".equals(i.getValue())) {
                            faker = new Faker(new Locale("by", "Be"));
                        }
                    }
                }

                for (int i = 0; i < Integer.parseInt(args[1]); i++){
                    str = generateStrings(faker);
                    if (args.length == 3 && isDouble(args[2]))
                        generateErrors(args[0], str,Double.parseDouble(args[2]));
                    else  generateErrors(args[0], str, 0);
                }
            } else System.err.println("Please enter an integer...");
        } else System.err.println("Please enter more parameters...");
    }

    private static String generateStrings(Faker f){
        String name = f.name().fullName();
        String address = f.address().fullAddress();
        String phone = f.phoneNumber().phoneNumber();
        f = null;
        return name + "; " + address + "; " + phone;
    }

    private static void generateErrors(String r, String s, double error){

        r = defineRegion(r);

        while (true){
            if (error == 0)
            {
                addErrors(r, s, 0);
                break;
            }
            if (error > 0 && error < 1)
            {
                if (counter < (int) 1/error)
                {
                    counter++;
                    addErrors(r, s, 0);
                    break;
                }
                else {
                    counter = 1;
                    addErrors(r, s, 1);
                    break;
                }
            }
            else if (error >= 1) {
                addErrors(r, s, error);
                break;
            }
        }
    }

    private static String defineRegion(String s){
        if (s.equals("be_By")) return "Belarus";
        if (s.equals("ru_Ru")) return "Russia";
        if (s.equals("en_Us")) return "USA";
        return "";
    }

    private static void addErrors(String r, String s, double error){

        String str = s;
        List<Character> l = new ArrayList<Character>();

        if (r.equals("Belarus")) l = getBel(l);
        if (r.equals("Russia")) l = getRus(l);
        if (r.equals("USA")) l = getEng(l);

        for (int i = 0; i < (int)error; i++)
        {
            double v = Math.random();
            char ch = l.get(0 + (int) (Math.random()*l.size()));

            if (v >= 0 && v <= 0.30)
            {
                int pos = (int) (0 + Math.random()*str.length());
                str = str.substring(0, pos) + str.substring(pos+1);
            }else if (v >= 0.31 && v <= 0.63)
            {
                int pos = (int) (0 + Math.random()*str.length());
                str = str.substring(0, pos) + ch + str.substring(pos);
            } else if (v >= 0.64 && v <= 1)
            {
                int pos = (int) (0 + Math.random()*str.length());
                char[] c = str.toCharArray();
                char temp = c[pos];
                if (pos+1 >= c.length){
                    c[pos] = c[pos-1];
                    c[pos-1] = temp;
                }else{
                    c[pos] = c[pos+1];
                    c[pos+1] = temp;
                }
                str = new String(c);
            }
        }
        l.clear();
        System.out.println(str);
    }

    private static List getRus(List l){

        for (int i = 1040; i < 1104; i++) l.add((char)i);
        for (int i = 49; i < 58; i++) l.add((char)i);

        return l;
    }

    private static List getBel(List l){

        l.add((char)1110);
        l.add((char)1118);
        l.add((char)1038);
        for (int i = 1040; i < 1048; i++) l.add((char)i);
        for (int i = 1050; i < 1065; i++) l.add((char)i);
        for (int i = 1067; i < 1080; i++) l.add((char)i);
        for (int i = 1082; i < 1097; i++) l.add((char)i);
        for (int i = 1099; i < 1104; i++) l.add((char)i);
        for (int i = 49; i < 58; i++) l.add( (char)i);

        return l;
    }

    private static List getEng(List l){

        for (int i = 65; i < 91; i++) l.add((char)i);
        for (int i = 97; i < 122; i++) l.add((char)i);
        for (int i = 49; i < 58; i++) l.add((char)i);

        return l;
    }

    private static boolean isInteger(String str){
        try
        {
            int n = Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    private static boolean isDouble(String str){
        try
        {
            double n = Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

}