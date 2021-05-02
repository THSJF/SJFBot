package com.meng.modules.touhou.thsss.replay;

public class KeyDataItem {
    public boolean arrowUp = false;
    public boolean arrowDown = false;
    public boolean arrowLeft = false;
    public boolean arrowRight = false;
    public boolean key_Shift = false;
    public boolean key_Z = false;
    public boolean key_X = false;
    public boolean key_C = false;
    public boolean key_Ctrl = false;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (arrowUp && arrowLeft) {
            builder.append("↖");
        } else if (arrowUp && arrowRight) {
            builder.append("↗");
        } else if (arrowDown && arrowLeft) {
            builder.append("↙");
        } else if (arrowDown && arrowRight) {
            builder.append("↘");
        } else if (arrowUp) {
            builder.append("↑");
        } else if (arrowDown) {
            builder.append("↓");
        } else if (arrowLeft) {
            builder.append("←");
        } else if (arrowRight) {
            builder.append("→");
        } else {
            builder.append("○");
        }
        if (key_Shift) {
            builder.append("f");
        }
        if (key_Z) {
            builder.append("s");
        }
        if (key_X) {
            builder.append("b");
        }
        if (key_Ctrl) {
            builder.append("c");
        }
        return builder.toString();
    }

    public void hex2Key(int keyValue) {
        keyValue >>= 7;
        key_Ctrl = keyValue % 2 == 1;
        keyValue >>= 1;
        key_C = keyValue % 2 == 1;
        keyValue >>= 1;
        key_X = keyValue % 2 == 1;
        keyValue >>= 1;
        key_Z = keyValue % 2 == 1;
        keyValue >>= 1;
        key_Shift = keyValue % 2 == 1;
        keyValue >>= 1;
        arrowRight = keyValue % 2 == 1;
        keyValue >>= 1;
        arrowLeft = keyValue % 2 == 1;
        keyValue >>= 1;
        arrowDown = keyValue % 2 == 1;
        keyValue >>= 1;
        arrowUp = keyValue % 2 == 1;
        keyValue >>= 1;
    }
}
