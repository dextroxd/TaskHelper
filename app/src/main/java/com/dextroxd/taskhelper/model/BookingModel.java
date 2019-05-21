package com.dextroxd.taskhelper.model;

public class BookingModel
{
    private String _btitle;
    private String _bimage;

    public BookingModel(String _btitle, String _bimage)
    {
        this._btitle = _btitle;
        this._bimage = _bimage;
    }


    public String get_btitle() {
        return _btitle;
    }


    public String get_bimage() {
        return _bimage;
    }
}
