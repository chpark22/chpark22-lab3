import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WordCounter {
        public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText{
                Pattern regex = Pattern.compile("[a-zA-Z0-9']+");
                Matcher regexmatcher = regex.matcher(text);
                int count = 0;
                boolean Found = false;
        
                while (regexmatcher.find()) {
                        String word = regexmatcher.group();
                        count++;
                        if (stopword != null && word.equals(stopword)) {
                                Found = true;
                                break;
                    }
                }
                if (stopword != null && !Found) {
                        throw new InvalidStopwordException("Couldn't find stopword: "+stopword);
                }
        
                if (count < 5) {
                        count++;
                        throw new TooSmallText("Only found " + count + " words.");
                }
        
                return count;
            }
        

        public static StringBuffer processFile(String way) throws EmptyFileException{
                StringBuilder l = new StringBuilder();
                try (BufferedReader m = new BufferedReader(new FileReader(way))) {
                        String lines;
                        while ((lines = m.readLine()) != null) {
                                l.append(lines).append(" ");
                        }
                }       
                catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                        System.out.print("Re-enter a valid file path: ");
                        Scanner scanner = new Scanner(System.in);
                        String re_way = scanner.nextLine();
                        scanner.close();
                        return processFile(re_way);
                }
            
                if (l.length() == 0) {
                        throw new EmptyFileException(way + " was empty");
                }
            
                return new StringBuffer(l.toString().trim());
                }
            
                public static void main(String[] args) {
                        Scanner scanner = new Scanner(System.in);
                        int option = 0;
                        String text = null;
                        String stopword = null;

                        while (option != 1 && option != 2) {
                                System.out.print("please choose valid option: ");
                                option = scanner.nextInt();
                                scanner.nextLine(); 
                        }
            
                        if (args.length > 1) {
                                stopword = args[1];
                        }
            
                        try {
                                if (option == 1) {
                                        System.out.print("Enter the file: ");
                                        String file = scanner.nextLine();
                                        StringBuffer filecontent = processFile(file);
                                        int wordCount = processText(filecontent, stopword);
                                        System.out.println("Found " + wordCount + " words.");
                                } if (option == 2) {
                                        System.out.print("Enter the text: ");
                                        text = scanner.nextLine();
                                        int wordCount = processText(new StringBuffer(text), stopword);
                                        System.out.println("Found " + wordCount + " words.");
                                }

                        } catch (InvalidStopwordException e) {
                                System.out.println(e.getMessage());
                                System.out.println("Enter a new stopword: ");
                                stopword = scanner.nextLine();
                                try {
                                        int wordCount = processText(new StringBuffer(text), stopword);
                                        System.out.println("Found " + wordCount + " words.");
                                } catch (InvalidStopwordException h) {
                                        System.out.println(h.getMessage());
                                } catch (TooSmallText o) {
                                        System.out.println(o.getMessage());
                                }

                        } catch (TooSmallText e) {
                                System.out.println(e.getMessage());
                                System.out.println("Too Short");
                        } catch (EmptyFileException e) {
                                System.out.println(e.getMessage());
                                System.out.println("warning: empty file");
                        }
            
                        scanner.close();
                }
        }
            
        class EmptyFileException extends IOException {
                public EmptyFileException(String message) {
                        super(message);
                }
        }
            
        class InvalidStopwordException extends Exception {
                public InvalidStopwordException(String message) {
                        super(message);
                }
        }
            
        class TooSmallText extends Exception {
                public TooSmallText(String message) {
                        super(message);
                }
        }
        

