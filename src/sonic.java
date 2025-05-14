import lib.StdAudio;
import java.awt.event.KeyEvent;
import static lib.Graphics.*;

class sonic {
    public static void main(String[] args) {
        enableDoubleBuffering();
        int lvl = 0;
        boolean isCompleted = false;
        while (true) {
            //иницилизация переменных, которые обновляюьтся каждую попытку
            double x1 = 0.5;
            double y1 = 0.5;
            double width = 1;
            double height = 1;
            int life = 3;
            int count = 10;
            int coin = 0;
            boolean isInFly = false;
            double x = 0.5;
            double y = 0.05;
            double y3 = y;
            double sonicHeight = 0.15;
            double sonicWidth = 0.15;
            double bombHeight = 0.08;
            double bombwWidth = 0.08;
            double x2 = 0;
            double x3 = 0;
            double stepX = 0.01;
            double stepY = 0.015;
            //проверка пока не будет нажата кнопка рестарта/след уровня
            while (true) {
                if (isCompleted) {
                    if ((lvl == 0) ||(isMousePressed() && mouseX() >= 0.7 && mouseX() <= 0.9 && mouseY() >= 0.07 && mouseY() <= 0.12)) {
                        count = lvl * 10;
                        break;
                    }
                }
                else {
                    if ((lvl == 0) ||(isMousePressed() && mouseX() >= 0.7 && mouseX() <= 0.9 && mouseY() >= 0.07 && mouseY() <= 0.12)) {
                        break;
                    }
                }
            }
            //сама игра
            while (life > 0) {
                if (coin >= count) break;
                picture(x1, y1, "src/lib/фон.jpg", width, height);
                picture(x, y, "src/lib/соник.png", sonicWidth, sonicHeight);
                picture(x2, y3, "src/lib/коин.png", bombwWidth, bombHeight);
                picture(x3, y3, "src/lib/Бомба.png", bombwWidth, bombHeight);
                picture(0.08,0.9, "src/lib/heart.png",0.1,0.1);
                picture(0.9,0.9, "src/lib/коин.png",0.08,0.08);
                text(0.13, 0.9, "" + life);
                text(0.83, 0.9,  coin + "/" + count);
                show();
                pause(1000 / 60);
                clear();
                if (x2 > 1) x2 = 0;
                if (x3 > 1) x3 = 0;
                //проверка на касание с бомбой
                if ((x3 + bombwWidth <= x + sonicWidth && x3 - bombwWidth >= x - sonicWidth) && (y3 + bombHeight >= y - sonicHeight && y3 - bombHeight >= y - sonicHeight)) {
                    StdAudio.playInBackground("src/lib/БомбаЗвук.wav");
                    life--;
                    x3 = 0;
                }
                //проверка на касание с монеткой
                if ((x2 + bombwWidth <= x + sonicWidth && x2 - bombwWidth >= x - sonicWidth) && (y3 + bombHeight >= y - sonicHeight && y3 - bombHeight >= y - sonicHeight)) {
                    StdAudio.playInBackground("src/lib/Монетазвук.wav");
                    coin++;
                    x2 = 0;
                }
                //движение персонажа
                if (isKeyPressed(KeyEvent.VK_D)) x = x + stepX;
                if (isKeyPressed(KeyEvent.VK_A)) x = x - stepX;
                if (isKeyPressed(KeyEvent.VK_SPACE) && y == 0.05) {
                    isInFly = true;
                    StdAudio.playInBackground("src/lib/ПрыжокЗВук.wav");
                }
                if (isInFly) y = y + stepY * 2.5;
                if (y > 0.29) isInFly = false;
                y = y - stepY;
                if (y > 0.3) y = 0.3;
                if (y < 0.05) y = 0.05;
                x2 = x2 + stepX;
                x3 = x3 + stepX * 2;
            }
            clear();
            //конец уровня, проверка проигрыш или выйгрыш
            if (coin >= count) {
                isCompleted = true;
                StdAudio.playInBackground("src/lib/VICTORYSound.wav");
                picture(0.5, 0.5, "src/lib/VICTORY.png", 1, 1);
                picture(0.8,0.1,"src/lib/Next-Thin-Master-Logo-2014-Reversed.jpg",0.25,0.18);
                lvl++;
            } else {
                isCompleted = false;
                lvl = 1;
                picture(0.5, 0.5, "src/lib/lose.jpg", 1, 1);
                picture(0.8, 0.1, "src/lib/replayjpg.jpg", 0.25, 0.18);
                StdAudio.playInBackground("src/lib/LoseSound.wav");
            }
            show();
        }
    }
}