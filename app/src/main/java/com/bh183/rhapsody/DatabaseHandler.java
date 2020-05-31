package com.bh183.rhapsody;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_lirikku";
    private final static String TABLE_LIRIK = "t_lirik";
    private final static String KEY_ID_LIRIK = "ID_Lirik";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_ALBUM = "Album";
    private final static String KEY_PENYANYI = "Penyanyi";
    private final static String KEY_ISI_LIRIK = "Isi_Lirik";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Context context;


    public DatabaseHandler(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_LIRIK = " CREATE TABLE " + TABLE_LIRIK
                + "(" + KEY_ID_LIRIK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_ALBUM + " TEXT, "
                + KEY_PENYANYI + " TEXT, " + KEY_ISI_LIRIK + " TEXT, "
                + KEY_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE_LIRIK);
        inisialisasiLirikAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE_LIRIK;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahLirik(Lirik dataLirik){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataLirik.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataLirik.getTanggal()));
        cv.put(KEY_GAMBAR, dataLirik.getGambar());
        cv.put(KEY_ALBUM, dataLirik.getAlbum());
        cv.put(KEY_PENYANYI, dataLirik.getPenyanyi());
        cv.put(KEY_ISI_LIRIK, dataLirik.getIsiLirik());
        cv.put(KEY_LINK, dataLirik.getLink());

        db.insert(TABLE_LIRIK, null, cv);
        db.close();
    }

    public void tambahLirik(Lirik dataLirik, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataLirik.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataLirik.getTanggal()));
        cv.put(KEY_GAMBAR, dataLirik.getGambar());
        cv.put(KEY_ALBUM, dataLirik.getAlbum());
        cv.put(KEY_PENYANYI, dataLirik.getPenyanyi());
        cv.put(KEY_ISI_LIRIK, dataLirik.getIsiLirik());
        cv.put(KEY_LINK, dataLirik.getLink());

        db.insert(TABLE_LIRIK, null, cv);
    }

    public void editLirik(Lirik dataLirik){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataLirik.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataLirik.getTanggal()));
        cv.put(KEY_GAMBAR, dataLirik.getGambar());
        cv.put(KEY_ALBUM, dataLirik.getAlbum());
        cv.put(KEY_PENYANYI, dataLirik.getPenyanyi());
        cv.put(KEY_ISI_LIRIK, dataLirik.getIsiLirik());
        cv.put(KEY_LINK, dataLirik.getLink());

        db.update(TABLE_LIRIK, cv, KEY_ID_LIRIK + "=?", new String[]{String.valueOf(dataLirik.getIdLirik())});
        db.close();
    }

    public void hapusLirik(int idLirik){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_LIRIK, KEY_ID_LIRIK + "=?", new String[]{String.valueOf(idLirik)});
        db.close();
    }

    public ArrayList<Lirik> getAllLirik(){
        ArrayList<Lirik> dataLirik = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_LIRIK;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do {
                Date tempDate =  new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er){
                    er.printStackTrace();
                }

                Lirik tempLirik = new Lirik(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)
                );

                dataLirik.add(tempLirik);
            } while (csr.moveToNext());
        }

        return dataLirik;
    }

    private String storeImageFile(int id){
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiLirikAwal(SQLiteDatabase db){
        int idLirik = 0;
        Date tempDate = new Date();

        try {
            tempDate = sdFormat.parse("31/10/1975");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Lirik lirik1 = new Lirik(
                idLirik,
                "Bohemian Rhapsody",
                tempDate,
                storeImageFile(R.drawable.bohemian),
                "A Night At The Opera",
                "Queen",
                "\"Bohemian Rhapsody\"\n" +
                        "\n" +
                        "Is this the real life?\n" +
                        "Is this just fantasy?\n" +
                        "Caught in a landslide,\n" +
                        "No escape from reality.\n" +
                        "\n" +
                        "Open your eyes,\n" +
                        "Look up to the skies and see,\n" +
                        "I'm just a poor boy, I need no sympathy,\n" +
                        "Because I'm easy come, easy go,\n" +
                        "Little high, little low,\n" +
                        "Any way the wind blows doesn't really matter to me, to me.\n" +
                        "\n" +
                        "Mama, just killed a man,\n" +
                        "Put a gun against his head,\n" +
                        "Pulled my trigger, now he's dead.\n" +
                        "Mama, life had just begun,\n" +
                        "But now I've gone and thrown it all away.\n" +
                        "\n" +
                        "Mama, ooh,\n" +
                        "Didn't mean to make you cry,\n" +
                        "If I'm not back again this time tomorrow,\n" +
                        "Carry on, carry on as if nothing really matters.\n" +
                        "\n" +
                        "Too late, my time has come,\n" +
                        "Sends shivers down my spine,\n" +
                        "Body's aching all the time.\n" +
                        "Goodbye, everybody, I've got to go,\n" +
                        "Gotta leave you all behind and face the truth.\n" +
                        "\n" +
                        "Mama, ooh (Any way the wind blows),\n" +
                        "I don't want to die,\n" +
                        "I sometimes wish I'd never been born at all.\n" +
                        "\n" +
                        "I see a little silhouetto of a man,\n" +
                        "Scaramouche, Scaramouche, will you do the Fandango?\n" +
                        "Thunderbolt and lightning very, very frightening me.\n" +
                        "(Galileo) Galileo.\n" +
                        "(Galileo) Galileo,\n" +
                        "Galileo Figaro\n" +
                        "Magnifico-o-o-o-o.\n" +
                        "\n" +
                        "I'm just a poor boy, nobody loves me.\n" +
                        "He's just a poor boy from a poor family,\n" +
                        "Spare him his life from this monstrosity.\n" +
                        "\n" +
                        "Easy come, easy go, will you let me go?\n" +
                        "Bismillah! No, we will not let you go. (Let him go!)\n" +
                        "Bismillah! We will not let you go. (Let him go!)\n" +
                        "Bismillah! We will not let you go. (Let me go!)\n" +
                        "Will not let you go. (Let me go!)\n" +
                        "Never let you go (Never, never, never, never let me go)\n" +
                        "Oh oh oh oh\n" +
                        "No, no, no, no, no, no, no\n" +
                        "Oh, mama mia, mama mia (Mama mia, let me go.)\n" +
                        "Beelzebub has a devil put aside for me, for me, for me.\n" +
                        "\n" +
                        "So you think you can stone me and spit in my eye?\n" +
                        "So you think you can love me and leave me to die?\n" +
                        "Oh, baby, can't do this to me, baby,\n" +
                        "Just gotta get out, just gotta get right outta here.\n" +
                        "\n" +
                        "(Ooooh, ooh yeah, ooh yeah)\n" +
                        "\n" +
                        "Nothing really matters,\n" +
                        "Anyone can see,\n" +
                        "Nothing really matters,\n" +
                        "Nothing really matters to me.\n" +
                        "\n" +
                        "Any way the wind blows... ",
                "https://www.youtube.com/watch?v=fJ9rUzIMcZQ"
        );

        tambahLirik(lirik1, db);
        idLirik++;

        try {
            tempDate = sdFormat.parse("26/06/1989");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Lirik lirik2 = new Lirik(
                idLirik,
                "Patience",
                tempDate,
                storeImageFile(R.drawable.patience),
                "-",
                "Guns N' Roses",
                "\"Patience\"\n" +
                        "\n" +
                        "1, 2, 1, 2, 3, 4\n" +
                        "\n" +
                        "Shed a tear 'cause I'm missin' you\n" +
                        "I'm still alright to smile\n" +
                        "Girl, I think about you every day now\n" +
                        "Was a time when I wasn't sure\n" +
                        "But you set my mind at ease\n" +
                        "There is no doubt\n" +
                        "You're in my heart now\n" +
                        "\n" +
                        "Said, woman, take it slow\n" +
                        "It'll work itself out fine\n" +
                        "All we need is just a little patience\n" +
                        "Said, sugar, make it slow\n" +
                        "And we come together fine\n" +
                        "All we need is just a little patience\n" +
                        "(Patience)\n" +
                        "\n" +
                        "I sit here on the stairs\n" +
                        "'Cause I'd rather be alone\n" +
                        "If I can't have you right now\n" +
                        "I'll wait, dear\n" +
                        "Sometimes I get so tense\n" +
                        "But I can't speed up the time\n" +
                        "But you know, love\n" +
                        "There's one more thing to consider\n" +
                        "\n" +
                        "Said, woman, take it slow\n" +
                        "And things will be just fine\n" +
                        "You and I'll just use a little patience\n" +
                        "Said, sugar, take the time\n" +
                        "'Cause the lights are shining bright\n" +
                        "You and I've got what it takes\n" +
                        "To make it, we won't fake it,\n" +
                        "I'll never break it\n" +
                        "'Cause I can't take it\n" +
                        "\n" +
                        "Little patience\n" +
                        "Need a little patience\n" +
                        "Just a little patience\n" +
                        "Some more patience\n" +
                        "Need some patience\n" +
                        "Could use some patience\n" +
                        "Gotta have some patience\n" +
                        "All it takes is patience\n" +
                        "Just a little patience\n" +
                        "Is all you need\n" +
                        "\n" +
                        "I been walkin' the streets at night\n" +
                        "Just tryin' to get it right\n" +
                        "Hard to see with so many around\n" +
                        "You know I don't like\n" +
                        "Being stuck in the crowd\n" +
                        "And the streets don't change\n" +
                        "But maybe the name\n" +
                        "I ain't got time for the game\n" +
                        "'Cause I need you\n" +
                        "Yeah, yeah, but I need you\n" +
                        "Oh, I need you\n" +
                        "Whoa, I need you\n" +
                        "All this time ",
                "https://www.youtube.com/watch?v=ErvgV4P6Fzc"
        );

        tambahLirik(lirik2, db);
        idLirik++;

        try {
            tempDate = sdFormat.parse("20/09/1994");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Lirik lirik3 = new Lirik(
                idLirik,
                "Always",
                tempDate,
                storeImageFile(R.drawable.always),
                "Cross Road",
                "Bon Jovi",
                "\"Always\"\n" +
                        "\n" +
                        "This Romeo is bleeding\n" +
                        "But you can't see his blood\n" +
                        "It's nothing but some feelings\n" +
                        "That this old dog kicked up\n" +
                        "\n" +
                        "It's been raining since you left me\n" +
                        "Now I'm drowning in the flood\n" +
                        "You see I've always been a fighter\n" +
                        "But without you I give up\n" +
                        "\n" +
                        "Now I can't sing a love song\n" +
                        "Like the way it's meant to be\n" +
                        "Well, I guess I'm not that good anymore\n" +
                        "But, baby, that's just me\n" +
                        "\n" +
                        "And I will love you, baby, always\n" +
                        "And I'll be there forever and a day, always\n" +
                        "I'll be there 'til the stars don't shine\n" +
                        "'Til the heavens burst and the words don't rhyme\n" +
                        "And I know when I die,\n" +
                        "You'll be on my mind\n" +
                        "And I'll love you always\n" +
                        "\n" +
                        "Now your pictures that you left behind\n" +
                        "Are just memories of a different life\n" +
                        "Some that made us laugh, some that made us cry\n" +
                        "One that made you have to say goodbye\n" +
                        "What I'd give to run my fingers through your hair\n" +
                        "To touch your lips, to hold you near\n" +
                        "When you say your prayers, try to understand\n" +
                        "I've made mistakes, I'm just a man\n" +
                        "\n" +
                        "When he holds you close, when he pulls you near\n" +
                        "When he says the words you've been needing to hear\n" +
                        "I'll wish I was him 'cause those words are mine\n" +
                        "To say to you 'til the end of time\n" +
                        "\n" +
                        "And I will love you, baby, always\n" +
                        "And I'll be there forever and a day, always\n" +
                        "\n" +
                        "If you told me to cry for you\n" +
                        "I could\n" +
                        "If you told me to die for you\n" +
                        "I would\n" +
                        "Take a look at my face\n" +
                        "There's no price I won't pay\n" +
                        "To say these words to you\n" +
                        "\n" +
                        "Well, there ain't no luck\n" +
                        "In these loaded dice\n" +
                        "But, baby, if you give me just one more try\n" +
                        "We can pack up our old dreams and our old lives\n" +
                        "We'll find a place where the sun still shines\n" +
                        "\n" +
                        "And I will love you, baby, always\n" +
                        "And I'll be there forever and a day, always\n" +
                        "I'll be there 'til the stars don't shine\n" +
                        "'Til the heavens burst and the words don't rhyme\n" +
                        "And I know when I die,\n" +
                        "You'll be on my mind\n" +
                        "And I'll love you, always ",
                "https://www.youtube.com/watch?v=9BMwcO6_hyA"
        );

        tambahLirik(lirik3, db);
        idLirik++;

        try {
            tempDate = sdFormat.parse("19/02/1995");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Lirik lirik4 = new Lirik(
                idLirik,
                "Don't Look Back In Anger",
                tempDate,
                storeImageFile(R.drawable.dontlookbackinanger),
                "(What's the Story) Morning Glory?",
                "Oasis",
                "\"Don't Look Back In Anger\"\n" +
                        "\n" +
                        "Slip inside the eye of your mind\n" +
                        "Don't you know you might find\n" +
                        "A better place to play\n" +
                        "You said that you'd never been\n" +
                        "But all the things that you've seen\n" +
                        "Will slowly fade away\n" +
                        "\n" +
                        "So I start a revolution from my bed\n" +
                        "'Cause you said the brains I had went to my head\n" +
                        "Step outside, summertime's in bloom\n" +
                        "Stand up beside the fireplace\n" +
                        "Take that look from off your face\n" +
                        "You ain't ever gonna burn my heart out\n" +
                        "\n" +
                        "And so, Sally can wait\n" +
                        "She knows it's too late as we're walking on by\n" +
                        "Her soul slides away\n" +
                        "\"But don't look back in anger\"\n" +
                        "I heard you say\n" +
                        "\n" +
                        "Take me to the place where you go\n" +
                        "Where nobody knows if it's night or day\n" +
                        "Please don't put your life in the hands\n" +
                        "Of a Rock 'n' Roll band\n" +
                        "Who'll throw it all away\n" +
                        "\n" +
                        "I'm gonna start a revolution from my bed\n" +
                        "'Cause you said the brains I had went to my head\n" +
                        "Step outside 'cause summertime's in bloom\n" +
                        "Stand up beside the fireplace, take that look from off your face\n" +
                        "'Cause you ain't ever gonna burn my heart out\n" +
                        "\n" +
                        "And so, Sally can wait\n" +
                        "She knows it's too late as she's walking on by\n" +
                        "My soul slides away\n" +
                        "\"But don't look back in anger,\" I heard you say\n" +
                        "\n" +
                        "So, Sally can wait\n" +
                        "She knows it's too late as we're walking on by\n" +
                        "Her soul slides away\n" +
                        "\"But don't look back in anger,\" I heard you say\n" +
                        "\n" +
                        "So, Sally can wait\n" +
                        "She knows it's too late as she's walking on by\n" +
                        "My soul slides away\n" +
                        "\"But don't look back in anger, don't look back in anger\"\n" +
                        "I heard you say, \"at least not today\" ",
                "https://www.youtube.com/watch?v=r8OipmKFDeM"
        );

        tambahLirik(lirik4, db);
    }
}
