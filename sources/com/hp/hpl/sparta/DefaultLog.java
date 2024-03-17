package com.hp.hpl.sparta;

/* compiled from: ParseSource */
class DefaultLog implements ParseLog {
    DefaultLog() {
    }

    public void error(String str, String str2, int i) {
        System.err.println(str2 + "(" + i + "): " + str + " (ERROR)");
    }

    public void warning(String str, String str2, int i) {
        System.out.println(str2 + "(" + i + "): " + str + " (WARNING)");
    }

    public void note(String str, String str2, int i) {
        System.out.println(str2 + "(" + i + "): " + str + " (NOTE)");
    }
}
