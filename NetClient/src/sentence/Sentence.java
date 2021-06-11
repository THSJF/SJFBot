package sentence;

import com.google.gson.annotations.SerializedName;
import java.util.HashSet;
import java.util.Set;

public class Sentence {
    public static class SentencesBean {
        @SerializedName("l")
        public Set<SingleSentence> first = new HashSet<>();
    
        public void add(String text, String from) {
            first.add(new SingleSentence(text, from));
        }
        
        public void add(String text) {
            first.add(new SingleSentence(text));
        }
    }

    public static class SingleSentence {
        @SerializedName("s")
        public String text;
        @SerializedName("t")
        public String from;

        public SingleSentence(String text) {
            this.text = text;
        }

        public SingleSentence(String text, String from) {
            this.text = text;
            this.from = from;
        }

        @Override
        public String toString() {
            return text;
        }  
    }
}
