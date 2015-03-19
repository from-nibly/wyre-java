package com.fromnibly.api.use;

import java.nio.ByteBuffer;

/**
 * Created by thato_000 on 3/11/2015.
 */
public class BinaryOrText {

    private final String text;
    private final ByteBuffer binary;

    public BinaryOrText(String text) {
        this.text = text;
        this.binary = null;
    }

    public BinaryOrText(ByteBuffer binary) {
        this.text = null;
        this.binary = binary;
    }

    public boolean isBinary() {
        return binary != null;
    }

    public boolean isText() {
        return text != null;
    }

    public String getText() {
        return this.text;
    }

    public ByteBuffer getBinary() {
        return this.binary;
    }
}
