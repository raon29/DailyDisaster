package com.dailydisaster;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Result extends Dialog implements View.OnClickListener {

    ImageView _showCard;
    TextView _explainCard;
    Button _goBack;
    String[] _explainText;
    int _selectedNumber;

    public Result(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        _showCard = (ImageView) findViewById(R.id.ShowCard);
        _explainCard = (TextView) findViewById(R.id.ExplainCard);
        _goBack = (Button) findViewById(R.id.GoBack);
        _explainText = new String[44];
        _explainText[0] = "The Fool\n자유, 형태에 빠지지 않음, 무사기, 순수, 천진난만, 가능성, 발상력, 천재.";
        _explainText[1] = "The Magician\n기원, 가능성, 활기, 재능, 기회, 감각, 창조.";
        _explainText[2] = "The High Priestess\n이지적, 이성적인 판단, 양식있는 행동, 합리적인 일의 진행";
        _explainText[3] = "The Empress\n번영, 풍양, 모권, 애정, 정열, 풍만, 포용력, 여성적 매력, 가정의 형성.";
        _explainText[4] = "The Emperor\n지배, 안정, 성취·달성, 남성적, 권위, 행동력, 의사, 책임감이 강함.";
        _explainText[5] = "The Hierophant\n자비, 연대·협조성, 신뢰, 존경, 상냥함, 배려, 자신, 법령·규율의 준수.";
        _explainText[6] = "The Lovers\n유혹과 싸움, 자신에의 신뢰, 가치관의 확립, 정열, 공감, 선택, 정, 깊은 결부, 결혼, 합일, 연애·성애, 취미의 몰두, 바람기, 조화, 선택, 낙관, 정, 시련의 극복.";
        _explainText[7] = "he Chariot\n승리, 정복, 원군, 행동력, 성공, 적극력, 돌진력, 개척 정신, 독립·해방.";
        _explainText[8] = "Justice\n공정·공평, 선행, 균형, 성의, 선의, 양립.";
        _explainText[9] = "The Hermit\n경험칙, 고상한 조언, 은닉, 정신, 신중, 사려깊음, 배려, 단독 행동.";
        _explainText[10] = "Wheel of Fortune\n전환점, 행운의 도래, 찬스, 변화, 결과, 만나, 해결, 정해진 운명.";
        _explainText[11] = "Strength\n역량의 크기, 강고한 의지, 불요불굴, 이성, 자제, 실행력, 지혜, 용기, 냉정, 지구전.";
        _explainText[12] = "The Hanged Man\n수행, 인내, 봉사, 노력, 시련, 착실, 억제, 타협.";
        _explainText[13] = "Death\n종말, 파멸, 이산, 종국, 청산, 결착, 죽음의 전조.";
        _explainText[14] = "Temperance\n조화, 자제, 절도, 헌신.";
        _explainText[15] = "The Devil\n배반, 구속, 타락.";
        _explainText[16] = "The Tower\n붕괴, 재해, 비극.";
        _explainText[17] = "The Star\n희망, 번쩍임, 소원이 이루어짐.";
        _explainText[18] = "The Moon\n불안정, 환혹, 현실 도피, 잠재하는 위험, 기만, 유예 없는 선택";
        _explainText[19] = "The Sun\n성공, 탄생, 축복, 약속된 장래.";
        _explainText[20] = "Judgement\n부활, 결과, 발전.";
        _explainText[21] = "The World\n완전, 총합, 성취.";

        _explainText[22] = "The Fool - REVERSE\n경솔, 제멋대로 굶, 낙오자.";
        _explainText[23] = "The Magician - REVERSE\n혼미, 무기력, 슬럼프, 배반, 겉돎, 바이오 리듬 저하, 소극성.";
        _explainText[24] = "The High Priestess - REVERSE\n불공평, 감정적, 분별력 저하, 신경과민, ";
        _explainText[25] = "The Empress - REVERSE\n좌절, 경솔, 허영심, 질투, 감정적, 낭비, 정서 불안정, 나태.";
        _explainText[26] = "The Emperor - REVERSE\n미숙, 횡포, 오안불손, 오만, 시먹음, 독단적, 의지 박약, 무책임.";
        _explainText[27] = "The Hierophant - REVERSE\n수구성(앙시앵 레짐), 속박, 주저, 불신감, 독선, 도피, 허영, 나태, 공연한 참견.";
        _explainText[28] = "The Lovers - REVERSE\n유혹, 부도덕, 실연, 헛돎, 무시, 집중력 결여, 공허, 결혼 생활의 파탄.";
        _explainText[29] = "The Chariot - REVERSE\n폭주, 부주의, 제멋대로 함, 실패, 독단력, 방약 무인, 초조해함, 좌절, 호전적.";
        _explainText[30] = "Justice - REVERSE\n부정, 편향, 불균형, 일방통행, 피고의 입장에 놓여짐.";
        _explainText[31] = "The Hermit - REVERSE\n폐쇄성, 음습, 소극적, 무계획, 오해, 비관적, 의혹.";
        _explainText[32] = "Wheel of Fortune - REVERSE\n정세의 급격한 악화, 엇갈림, 격하, 사고의 도래.";
        _explainText[33] = "Strength - REVERSE\n어리광, 소극성, 무기력, 임무 전가, 우유부단, 권세를 텀.";
        _explainText[34] = "The Hanged Man - REVERSE\n헛수고, 오기, 태만, 자포자기, 욕망에 짐.";
        _explainText[35] = "Death - REVERSE\n재시작, 신전개, 상승, 좌절에서 회복.";
        _explainText[36] = "Temperance - REVERSE\n낭비, 소모, 생활의 혼란.";
        _explainText[37] = "The Devil - REVERSE\n회복, 각성, 새로운 만남.";
        _explainText[38] = "The Tower - REVERSE\n긴박, 갑작스런 사고, 오해.";
        _explainText[39] = "The Star - REVERSE\n혼실망, 무기력, 허황된 소망.";
        _explainText[40] = "The Moon - REVERSE\n실패가 되지 않는 잘못, 과거로부터의 탈각, 서서히 호전, (막연한) 미래로의 희망, 뛰어난 직감";
        _explainText[41] = "The Sun - REVERSE\n부진, 낙담, 쇠퇴, 낙태·유산.";
        _explainText[42] = "Judgement - REVERSE\n회한, 한계, 나쁜 소식.";
        _explainText[43] = "The World - REVERSE\n미완성, 임계점, 조화의 붕괴.";

        _selectedNumber = (int)((Math.random()*100)%44);
        switch(_selectedNumber){
            case 0:_showCard.setImageResource(R.drawable.card0);break;
            case 1:_showCard.setImageResource(R.drawable.card1);break;
            case 2:_showCard.setImageResource(R.drawable.card2);break;
            case 3:_showCard.setImageResource(R.drawable.card3);break;
            case 4:_showCard.setImageResource(R.drawable.card4);break;
            case 5:_showCard.setImageResource(R.drawable.card5);break;
            case 6:_showCard.setImageResource(R.drawable.card6);break;
            case 7:_showCard.setImageResource(R.drawable.card7);break;
            case 8:_showCard.setImageResource(R.drawable.card8);break;
            case 9:_showCard.setImageResource(R.drawable.card9);break;
            case 10:_showCard.setImageResource(R.drawable.card10);break;
            case 11:_showCard.setImageResource(R.drawable.card11);break;
            case 12:_showCard.setImageResource(R.drawable.card12);break;
            case 13:_showCard.setImageResource(R.drawable.card13);break;
            case 14:_showCard.setImageResource(R.drawable.card14);break;
            case 15:_showCard.setImageResource(R.drawable.card15);break;
            case 16:_showCard.setImageResource(R.drawable.card16);break;
            case 17:_showCard.setImageResource(R.drawable.card17);break;
            case 18:_showCard.setImageResource(R.drawable.card18);break;
            case 19:_showCard.setImageResource(R.drawable.card19);break;
            case 20:_showCard.setImageResource(R.drawable.card20);break;
            case 21:_showCard.setImageResource(R.drawable.card21);break;
            case 22:_showCard.setImageResource(R.drawable.card22);break;
            case 23:_showCard.setImageResource(R.drawable.card23);break;
            case 24:_showCard.setImageResource(R.drawable.card24);break;
            case 25:_showCard.setImageResource(R.drawable.card25);break;
            case 26:_showCard.setImageResource(R.drawable.card26);break;
            case 27:_showCard.setImageResource(R.drawable.card27);break;
            case 28:_showCard.setImageResource(R.drawable.card28);break;
            case 29:_showCard.setImageResource(R.drawable.card29);break;
            case 30:_showCard.setImageResource(R.drawable.card30);break;
            case 31:_showCard.setImageResource(R.drawable.card31);break;
            case 32:_showCard.setImageResource(R.drawable.card32);break;
            case 33:_showCard.setImageResource(R.drawable.card33);break;
            case 34:_showCard.setImageResource(R.drawable.card34);break;
            case 35:_showCard.setImageResource(R.drawable.card35);break;
            case 36:_showCard.setImageResource(R.drawable.card36);break;
            case 37:_showCard.setImageResource(R.drawable.card37);break;
            case 38:_showCard.setImageResource(R.drawable.card38);break;
            case 39:_showCard.setImageResource(R.drawable.card39);break;
            case 40:_showCard.setImageResource(R.drawable.card40);break;
            case 41:_showCard.setImageResource(R.drawable.card41);break;
            case 42:_showCard.setImageResource(R.drawable.card42);break;
            case 43:_showCard.setImageResource(R.drawable.card43);break;
        }
        _explainCard.setText(_explainText[_selectedNumber]);
        _goBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == _goBack)
            dismiss();
    }
}
