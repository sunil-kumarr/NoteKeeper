package com.capstone.notekeeper.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Vibrator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capstone.notekeeper.R;

import java.util.ArrayList;
import java.util.Random;

public class BrainPrepActivity extends AppCompatActivity {

    Button startbutton;
    Button button1,button2,button3,add,sub,mul,div;
    Button yesbutton;
    Button nobutton;
    Button button4;
    TextView resluttextview;
    TextView pointtextView;
    TextView sumtextview;
    TextView timertextview;
    TextView playgn;
    ImageView imageView;
    RelativeLayout mainrelativelayout,optionRelativeLayout;
    Vibrator v;
    //MediaPlayer mediaPlayer;
    //generalrelativelayout is the layout of the science questions.
    //mainrelativelayout is the layout of the maths questions.
    ArrayList<Integer> arr = new ArrayList<>();
    int pos_of_correct;
    int correct = 0;
    int noOfQues = 0;
    int tag = 0;


    public void playagain(final View view)
    {
        correct = 0;
        noOfQues = 0;
        //playagainbutton.setVisibility(View.INVISIBLE);
        timertextview.setText("30s");
        pointtextView.setText("0/0");
        resluttextview.setText("");
        yesbutton.setVisibility(View.INVISIBLE);
        nobutton.setVisibility(View.INVISIBLE);
        playgn.setVisibility(View.INVISIBLE);
        //Log.i("Tag is:", Integer.toString(tag));
        if(tag==4)
            generateAdditionQuestion();
        if(tag==5)
            generateSubtractionQuestion();
        if(tag==6)
            generateMultiplicationQuestion();
        if(tag==7)
            generateDivisionQuestion();


        new CountDownTimer(30100,1000)
        {
            @Override
            public void onTick(long l) {

                timertextview.setText(String.valueOf(l/1000) + "s");
            }

            @Override
            public void onFinish() {
                // Log.i("Timer :" , "Oye khatam ho gya :P");
                timertextview.setText("0s");
                playgn.setVisibility(View.VISIBLE);
                yesbutton.setVisibility(View.VISIBLE);
                nobutton.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                resluttextview.setText("Your Score: " + Integer.toString(correct)+"/"+Integer.toString(noOfQues));
                MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.buzzer);
                mplayer.start();
                //mainrelativelayout.setVisibility(View.INVISIBLE);
                //startbutton.setVisibility(Button.VISIBLE);


            }
        }.start();

    }


    public void over(View view)
    {
        new MainActivity().finish();
        System.exit(0);
    }

    public void  chooseAnswer(View view)
    {
        //Log.i("Tag",(String) view.getTag());
        //Log.i("Location", Integer.toString(pos_of_correct));

        if(view.getTag().toString().equals(Integer.toString(pos_of_correct)))
        {
            //Log.i("Result","Correct Answer");
            correct++;
            noOfQues++;
            correct();
        }
        else
        {
            //Log.i("Result","Incorrect Answer");
            //resluttextview.setText("Incorrect!");
            incorrect();
            noOfQues++;
        }
        pointtextView.setText(Integer.toString(correct)+"/"+Integer.toString(noOfQues));
        if(tag==4)
            generateAdditionQuestion();
        if(tag==5)
            generateSubtractionQuestion();
        if(tag==6)
            generateMultiplicationQuestion();
        if(tag==7)
            generateDivisionQuestion();

    }


    public void correct()
    {
        //imageView.setVisibility(View.VISIBLE);
        imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.correct));
    };

    public void incorrect()
    {
        imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.wrong));
        v.vibrate(200);

        //imageView.setBackgroundResource(R.drawable.wrong);

    };

    public void generateAdditionQuestion()
    {
        Random rand = new Random();
        int a = rand.nextInt(31);
        int b = rand.nextInt(31);
        sumtextview.setText(Integer.toString(a)+" + "+Integer.toString(b));

        int sum = a+b;
        pos_of_correct = rand.nextInt(4);
        arr.clear();
        for(int i=0;i<4;i++)
        {
            if(i==pos_of_correct)
            {
                //Log.i("I is", Integer.toString(i));
                //Log.i("pos is ", Integer.toString(pos_of_correct));
                arr.add(sum);
            }
            else
            {
                int incorrect_ans = rand.nextInt(61);
                while(incorrect_ans==sum)
                {
                    incorrect_ans = rand.nextInt(61);
                }
                arr.add(incorrect_ans);
            }
        }
        button1.setText(Integer.toString(arr.get(0)));
        button2.setText(Integer.toString(arr.get(1)));
        button3.setText(Integer.toString(arr.get(2)));
        button4.setText(Integer.toString(arr.get(3)));

    }



    public void generateSubtractionQuestion()
    {
        Random rand = new Random();
        int a = rand.nextInt(31);
        int b = rand.nextInt(31);
        while(a<b)
        {
            a = rand.nextInt(31);
            b = rand.nextInt(31);

        }
        sumtextview.setText(Integer.toString(a)+" - "+Integer.toString(b));

        int difference = a-b;
        pos_of_correct = rand.nextInt(4);
        arr.clear();
        for(int i=0;i<4;i++)
        {
            if(i==pos_of_correct)
            {
                //Log.i("I is", Integer.toString(i));
                //Log.i("pos is ", Integer.toString(pos_of_correct));
                arr.add(difference);
            }
            else
            {
                int incorrect_ans = rand.nextInt(61);
                while(incorrect_ans==difference)
                {
                    incorrect_ans = rand.nextInt(61);
                }
                arr.add(incorrect_ans);
            }
        }
        button1.setText(Integer.toString(arr.get(0)));
        button2.setText(Integer.toString(arr.get(1)));
        button3.setText(Integer.toString(arr.get(2)));
        button4.setText(Integer.toString(arr.get(3)));
    }




    public void generateMultiplicationQuestion()
    {
        Random rand = new Random();
        int a = rand.nextInt(20);
        int b = rand.nextInt(10);
        sumtextview.setText(Integer.toString(a)+" X "+Integer.toString(b));

        int product = a*b;
        pos_of_correct = rand.nextInt(4);
        arr.clear();
        for(int i=0;i<4;i++)
        {
            if(i==pos_of_correct)
            {
                //Log.i("I is", Integer.toString(i));
                //Log.i("pos is ", Integer.toString(pos_of_correct));
                arr.add(product);
            }
            else
            {
                int incorrect_ans = rand.nextInt(100);
                while(incorrect_ans==product)
                {
                    incorrect_ans = rand.nextInt(100);
                }
                arr.add(incorrect_ans);
            }
        }
        button1.setText(Integer.toString(arr.get(0)));
        button2.setText(Integer.toString(arr.get(1)));
        button3.setText(Integer.toString(arr.get(2)));
        button4.setText(Integer.toString(arr.get(3)));
    }

    public void generateDivisionQuestion()
    {
        Random rand = new Random();
        int a = rand.nextInt(20);
        int b = rand.nextInt(10);
        int divisor = b;
        b=a*b;
        sumtextview.setText(Integer.toString(b)+" / "+Integer.toString(a));
        pos_of_correct = rand.nextInt(4);
        arr.clear();
        for(int i=0;i<4;i++)
        {
            if(i==pos_of_correct)
            {
                //Log.i("I is", Integer.toString(i));
                //Log.i("pos is ", Integer.toString(pos_of_correct));
                arr.add(divisor);
            }
            else
            {
                int incorrect_ans = rand.nextInt(100);
                while(incorrect_ans==divisor)
                {
                    incorrect_ans = rand.nextInt(100);
                }
                arr.add(incorrect_ans);
            }
        }
        button1.setText(Integer.toString(arr.get(0)));
        button2.setText(Integer.toString(arr.get(1)));
        button3.setText(Integer.toString(arr.get(2)));
        button4.setText(Integer.toString(arr.get(3)));
    }




    /*

    TAGS --
    4 -- Addition
    5 -- Subtraction
    6 -- Multiplication

     */

    public void addition(View view)
    {
        //Log.i("Button: " , "Addition Button");
        tag=4;
        add.setVisibility(Button.INVISIBLE);
        sub.setVisibility(Button.INVISIBLE);
        div.setVisibility(Button.INVISIBLE);
        mul.setVisibility(Button.INVISIBLE);
        optionRelativeLayout.setVisibility(RelativeLayout.INVISIBLE);
        mainrelativelayout.setVisibility(RelativeLayout.VISIBLE);
        playagain(findViewById(R.id.mathsrelativeLayout));
    }

    public void subtraction(View view)
    {
        //Log.i("Button: " , "Subtraction Button");
        tag=5;
        add.setVisibility(Button.INVISIBLE);
        sub.setVisibility(Button.INVISIBLE);
        div.setVisibility(Button.INVISIBLE);
        mul.setVisibility(Button.INVISIBLE);
        optionRelativeLayout.setVisibility(RelativeLayout.INVISIBLE);
        mainrelativelayout.setVisibility(RelativeLayout.VISIBLE);
        playagain(findViewById(R.id.mathsrelativeLayout));
    }

    public void multiplication(View view)
    {
        //Log.i("Button: " , "Subtraction Button");
        tag=6;
        add.setVisibility(Button.INVISIBLE);
        sub.setVisibility(Button.INVISIBLE);
        mul.setVisibility(Button.INVISIBLE);
        div.setVisibility(Button.INVISIBLE);
        mainrelativelayout.setVisibility(RelativeLayout.VISIBLE);
        playagain(findViewById(R.id.mathsrelativeLayout));
    }

    public void division(View view)
    {
        //Log.i("Button: " , "Subtraction Button");
        tag=7;
        add.setVisibility(Button.INVISIBLE);
        sub.setVisibility(Button.INVISIBLE);
        mul.setVisibility(Button.INVISIBLE);
        div.setVisibility(Button.INVISIBLE);
        mainrelativelayout.setVisibility(RelativeLayout.VISIBLE);
        playagain(findViewById(R.id.mathsrelativeLayout));
    }

    public void start(View view)
    {
        startbutton.setVisibility(View.INVISIBLE);
        optionRelativeLayout.setVisibility(RelativeLayout.VISIBLE);
    }


    public void yes(View view)
    {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        //mainrelativelayout.setVisibility(View.INVISIBLE);
        //startbutton.setVisibility(View.VISIBLE);
        //start(findViewById(R.id.mathsrelativeLayout));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brain_prep);

        startbutton = (Button) findViewById(R.id.start_button);
        sumtextview = (TextView) findViewById(R.id.sumTextView);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        add = (Button) findViewById(R.id.addition);
        sub = (Button) findViewById(R.id.subtraction);
        mul = (Button) findViewById(R.id.multiplication);
        div = (Button) findViewById(R.id.division);
        yesbutton = (Button) findViewById(R.id.yes);
        nobutton = (Button) findViewById(R.id.no);
        resluttextview = (TextView) findViewById(R.id.resulttextview);
        pointtextView = (TextView) findViewById(R.id.pointsTextView);
        timertextview = (TextView) findViewById(R.id.timer);
        playgn = (TextView) findViewById(R.id.playagain);
        mainrelativelayout = (RelativeLayout) findViewById(R.id.mathsrelativeLayout);
        optionRelativeLayout = (RelativeLayout) findViewById(R.id.optionrelativlayout);
        imageView = (ImageView) findViewById(R.id.imageView);
        v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

    }


}
