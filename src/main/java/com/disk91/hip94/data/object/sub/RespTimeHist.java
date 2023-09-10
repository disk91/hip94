package com.disk91.hip94.data.object.sub;

public class RespTimeHist {

    protected int lower10ms;
    protected int lower30ms;
    protected int lower50ms;
    protected int lower100ms;
    protected int lower250ms;
    protected int lower500ms;
    protected int lower1s;
    protected int lower2s;
    protected int lower3s;
    protected int over3s;

    // ---

    public void init() {
        this.lower10ms = 0;
        this.lower30ms = 0;
        this.lower50ms = 0;
        this.lower100ms = 0;
        this.lower250ms = 0;
        this.lower500ms = 0;
        this.lower1s = 0;
        this.lower2s = 0;
        this.lower3s = 0;
        this.over3s = 0;
    }

    public void addOneTime(long t) {
        if ( t < 10 ) this.lower10ms++;
        else if ( t < 30 ) this.lower30ms++;
        else if ( t < 50 ) this.lower50ms++;
        else if ( t < 100 ) this.lower100ms++;
        else if ( t < 250 ) this.lower250ms++;
        else if ( t < 500 ) this.lower500ms++;
        else if ( t < 1000 ) this.lower1s++;
        else if ( t < 2000 ) this.lower2s++;
        else if ( t < 3000 ) this.lower3s++;
        else this.over3s++;
    }

    // ---

    public int getLower10ms() {
        return lower10ms;
    }

    public void setLower10ms(int lower10ms) {
        this.lower10ms = lower10ms;
    }

    public int getLower30ms() {
        return lower30ms;
    }

    public void setLower30ms(int lower30ms) {
        this.lower30ms = lower30ms;
    }

    public int getLower50ms() {
        return lower50ms;
    }

    public void setLower50ms(int lower50ms) {
        this.lower50ms = lower50ms;
    }

    public int getLower100ms() {
        return lower100ms;
    }

    public void setLower100ms(int lower100ms) {
        this.lower100ms = lower100ms;
    }

    public int getLower250ms() {
        return lower250ms;
    }

    public void setLower250ms(int lower250ms) {
        this.lower250ms = lower250ms;
    }

    public int getLower500ms() {
        return lower500ms;
    }

    public void setLower500ms(int lower500ms) {
        this.lower500ms = lower500ms;
    }

    public int getLower1s() {
        return lower1s;
    }

    public void setLower1s(int lower1s) {
        this.lower1s = lower1s;
    }

    public int getLower2s() {
        return lower2s;
    }

    public void setLower2s(int lower2s) {
        this.lower2s = lower2s;
    }

    public int getLower3s() {
        return lower3s;
    }

    public void setLower3s(int lower3s) {
        this.lower3s = lower3s;
    }

    public int getOver3s() {
        return over3s;
    }

    public void setOver3s(int over3s) {
        this.over3s = over3s;
    }
}
