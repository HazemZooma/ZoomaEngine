package behaviors;

import engine.core.SceneManager;
import engine.core.input.MouseInputData;
import objects.core.GameObject;

public class SimpleShootingBehavior implements ShootingBehavior {

    private int cooldown = 0;
    private MouseInputData mouseInputData;
    private GameObject bullet;

    public SimpleShootingBehavior(MouseInputData mouseInputData, GameObject bullet) {
        this.mouseInputData = mouseInputData;
        this.bullet = bullet;
    }

    @Override
    public void shoot(GameObject shooter) {
        if (cooldown > 15) {
            cooldown--;
            return;
        }

        if (mouseInputData.isPressed() && bullet!= null) {
            SceneManager.getInstance().getCurrentScene().add(bullet);
//            cooldown += 1;
        }
    }
}




