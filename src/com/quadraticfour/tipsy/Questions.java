package com.quadraticfour.tipsy;


import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Questions extends Activity {
	static int total = 3;
	int correct = 0;
	int wrong = 0;
	int timeleft = 0;
	ArrayList answered = new ArrayList();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_questions);
		
		final TextView tvv = (TextView) findViewById(R.id.textView2);
		final ProgressBar pg = (ProgressBar) findViewById(R.id.progressBar1);
		RelativeLayout mainbase = (RelativeLayout) findViewById(R.id.AnswerBase);
		
			pg.setMax(30000);
			boolean cond = true;
			new CountDownTimer(30000, 1000) {//CountDownTimer(edittext1.getText()+edittext2.getText()) also parse it to long
				 public void onTick(long millisUntilFinished) {
				     tvv.setText(String.valueOf(millisUntilFinished / 1000));
				     pg.setProgress((int) millisUntilFinished);
				  //here you can have your logic to set text to edittext
				 }
				 
				 public void onFinish() {
					 end();
				 }}.start();
				 
				 startQuiz();
				 
		}
		
		
	
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questions, menu);
		return true;
	}
	


	Button.OnClickListener RightListener = new Button.OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	TextView tv = (TextView) findViewById(R.id.timeView);
	    	correct += 1;
	    	if (correct + wrong != total){
	    		startQuiz();
	    	}else{
	    		ProgressBar pg = (ProgressBar) findViewById(R.id.progressBar1);
	    		timeleft = pg.getProgress();
	    		end();
	    	}
	          
	}
	};
	    
	Button.OnClickListener WrongListener = new Button.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	wrong += 1;
		    	if (correct + wrong != total){
		    		startQuiz();
		    	}else{
		    		ProgressBar pg = (ProgressBar) findViewById(R.id.progressBar1);
		    		timeleft = pg.getProgress();
		    		end();
		    	}
		}
	};

	
	
	
	public void startQuiz(){
		 
		TextView tv = (TextView) findViewById(R.id.timeView);
		RelativeLayout mainbase = (RelativeLayout) findViewById(R.id.AnswerBase);
		mainbase.removeAllViews();
		Random rand = new Random();
		int i = 0;
		boolean cond = true;
		do{
			 i = rand.nextInt(total) + 1;
			
			if (!answered.contains(i)){
				cond = false;
			}
		}while (cond);
		
		
		if (i == 1){
			Pair<String,LinearLayout> q_a = QuestionOne();
			tv.setText(q_a.first);
			mainbase.addView(q_a.second);
			answered.add(1);
		}else if(i == 2){
			Pair<String,LinearLayout> g_a = QuestionTwo();
			tv.setText(g_a.first);
			mainbase.addView(g_a.second);
		answered.add(2);
		}else if(i == 3){
			Pair<String,LinearLayout> w_a = QuestionThree();
			tv.setText(w_a.first);
			mainbase.addView(w_a.second);
			answered.add(3);
		
		
		}
	}
	
	public Pair<String,LinearLayout> QuestionOne(){
		Random rand = new Random();
		int i = rand.nextInt(40) + 1;
		//Create Answers
		LinearLayout base = new LinearLayout(this);
		base.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout left = new LinearLayout(this);
		left.setOrientation(LinearLayout.VERTICAL);
		left.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		LinearLayout mid = new LinearLayout(this);
		mid.setOrientation(LinearLayout.VERTICAL);
		mid.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		LinearLayout right = new LinearLayout(this);
		right.setOrientation(LinearLayout.VERTICAL);
		right.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		base.addView(left);base.addView(mid);base.addView(right);
		
		
		//Right Answer
		Button but = new Button(this);
		int side = rand.nextInt(3) + 1;
		if (side == 1){
			left.addView(but);
		}else if(side == 2){
			mid.addView(but);
		}else if(side == 3){
			right.addView(but);
		}
		but.setText(String.valueOf(i));
		but.setOnClickListener(RightListener);
		
		//WrongAnswers
		for (int j = 1; j < 9; j++){
			but = new Button(this);
			int temp = rand.nextInt(40) + 1;
			do{
				temp = rand.nextInt(40) + 1;
			}while(temp == i);
			
			but.setText(String.valueOf(temp));
			but.setOnClickListener(WrongListener);
			side = rand.nextInt(3) + 1;
			
			if (left.getChildCount() < 3){
				left.addView(but);
			}else if (mid.getChildCount() < 3){
				mid.addView(but);
			}else if (right.getChildCount() < 3){
				right.addView(but);
			}
			
		}
		
		String question = "Click on the number " + i + ".";
		
		Pair<String,LinearLayout> result = new Pair<String, LinearLayout>(question, base);
		return result;
	}
	
	public Pair<String,LinearLayout> QuestionTwo(){
		String return_str = "";
		Random rand = new Random();
		String[] states = getResources().getStringArray(R.array.states);
		int num_state = rand.nextInt(50) + 1;
		
		String j = states[num_state];
		int answer = 0;
		int str_len = j.length();
		int l_index = rand.nextInt(str_len);
		int num_1 = rand.nextInt(10) + 1;
		int num_2 = rand.nextInt(10) + 1;
		if (l_index > 2)
		{
			return_str += String.format("I drove to %s with %d apple(s) in a bag and %d orange(s) in a basket,"
				+ " what is the %dth letter in the state I drove in?\n",j,num_1,num_2,l_index+1);
			//System.out.printf("I drove to %s with %d apples in a bag and %d oranges in a basket,"
				//+ " what is the %dth letter in the state I drove in?\n",j,num_1,num_2,l_index+1);
		}
		if(l_index == 2)
		{
			return_str += String.format("I drove to %s with %d apple(s) in a bag and %d orange(s) in a basket,"
					+ " what is the 3rd letter in the state I drove in?\n",j,num_1,num_2);
			//System.out.printf("I drove to %s with %d apples in a bag and %d oranges in a basket,"
				//	+ " what is the 3rd letter in the state I drove in\n",j,num_1,num_2,l_index+1);
		}
		if (l_index == 1)
		{
			return_str += String.format("I drove to %s with %d apples() in a bag and %d orange(s) in a basket,"
					+ " what is the 2nd letter in the state I drove in?\n",j,num_1,num_2);
			//System.out.printf("I drove to %s with %d apples in a bag and %d oranges in a basket,"
				//	+ " what is the 2nd letter in the state I drove in?\n",j,num_1,num_2);
		}
		if (l_index == 0)
		{
			return_str += String.format("I drove to %s with %d apple(s) in a bag and %d orange(s) in a basket,"
					+ " what is the 1st letter in the state I drove in?\n",j,num_1,num_2);
			//System.out.printf("I drove to %s with %d apples in a bag and %d oranges in a basket,"
				//	+ " what is the 1st letter in the state I drove in?\n",j,num_1,num_2);
		}
		ArrayList answers = new ArrayList();
		ArrayList letters = new ArrayList();
		int counter = 0;
		int num = 0;
		for (int i=0; i<4; i++)
		{
			int k = rand.nextInt(str_len);
			if (k == l_index || answers.contains(k) == true || letters.contains(j.charAt(k)) || j.charAt(k) == j.charAt(l_index))
			{
				i--;
			}
			else
			{
				letters.add(j.charAt(k));
				answers.add(k);
				num = i+1;
			}
			if (counter > 100)
			{
				num = i+1;
				break;
			}
			counter++;
		}
		answers.add(l_index);
		
		int i = 0;
		
		ArrayList r_answers = new ArrayList();
		int r_num = 0;
		while (i < num+1)
		{
			r_num = rand.nextInt(num+1);
			if (r_answers.contains(r_num))
				continue;
			else
			{
				r_answers.add(r_num);
				if (Integer.parseInt(answers.get(r_num).toString()) == l_index)
				{
					answer = i + 1;
				}
				return_str += String.format("%d ) %s\n",i+1, j.charAt(Integer.parseInt(answers.get(r_num).toString())));
				//System.out.printf("%d ) %s\n",i+1, j.charAt((int)answers.get(r_num)));
				i++;
			}
			
		}
		
		return_str += answer;
		
		String splits[] = return_str.split("\n");
		
		int correct_ans = Integer.parseInt(splits[6]);
		
		LinearLayout base = new LinearLayout(this);
		base.setOrientation(LinearLayout.VERTICAL);
		
		for (int in = 1; in < 6; in++){
			Button button = new Button(this);
			button.setText(splits[in]);
			
			
			if (in == correct_ans){
				button.setOnClickListener(RightListener);
			}else{
				button.setOnClickListener(WrongListener);
			}
			base.addView(button);
		}
		
		Pair<String,LinearLayout> result = new Pair<String,LinearLayout>(splits[0], base);
		return result;
		
	}

	public Pair<String,LinearLayout> QuestionThree(){
		String return_str = "";
		String fruit_str = "";
		Random rand = new Random();
		String[] states = getResources().getStringArray(R.array.states);
		int num_state = rand.nextInt(50) + 1;
		String j = states[num_state];
		int answer = 0;
		int apple_num = rand.nextInt(10) + 1;
		int orange_num = rand.nextInt(10) + 1;
		int num_to_check = 0;
		if (rand.nextInt(101)%2 == 0)
		{
			num_to_check = apple_num;
			fruit_str = "apples";
		}
		else
		{
			num_to_check = orange_num;
			fruit_str = "oranges";
		}
		return_str += String.format("I drove to %s with %d apples in a bag and %d oranges in a "
				+ "basket, how many %s do I have?\n",j,apple_num,orange_num,fruit_str);
		//System.out.printf("I drove to %s with %d apples in a bag and %d oranges in a "
			//	+ "basket, how many %s do I have?\n",j,apple_num,orange_num,fruit_str);
		
		ArrayList answers = new ArrayList();
		for (int i=0; i<4; i++)
		{
			int k = rand.nextInt(10);
			if (k == num_to_check||answers.contains(k))
				i--;
			else
				answers.add(k);
		}
		answers.add(num_to_check);
		
		ArrayList r_answers = new ArrayList();
		int i = 0;
		int r_num = 0;
		while (i < 5)
		{
			r_num = rand.nextInt(5);
			if (r_answers.contains(r_num))
				continue;
			else
			{
				r_answers.add(r_num);
				if (Integer.parseInt(answers.get(r_num).toString()) == num_to_check)
				{
					answer = i + 1;
				}
				return_str += String.format("%d ) %s\n",i+1, answers.get(r_num));
				//System.out.printf("%d ) %s\n",i+1, answers.get(r_num));
				i++;
			}
		}
			
		return_str += answer;
		
		String splits[] = return_str.split("\n");
		
		int correct_ans = Integer.parseInt(splits[6]);
		
		LinearLayout base = new LinearLayout(this);
		base.setOrientation(LinearLayout.VERTICAL);
		
		for (int in = 1; in < 6; in++){
			Button button = new Button(this);
			button.setText(splits[in]);
			
			
			if (in == correct_ans){
				button.setOnClickListener(RightListener);
			}else{
				button.setOnClickListener(WrongListener);
			}
			base.addView(button);
		}
		
		Pair<String,LinearLayout> result = new Pair<String,LinearLayout>(splits[0], base);
		return result;
	}
	
	public void end(){
		Intent main = new Intent(this, MainPage.class);
		main.putExtra("Score", String.valueOf(correct) + "/" +String.valueOf(total) + "/" + String.valueOf(timeleft));
		startActivity(main);
	}
	}

	
