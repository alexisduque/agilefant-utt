package fi.hut.soberit.agilefant.exportimport;


import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alex
 */
public class ListToStringXStreamConverter implements Converter {

    private String alias;

    public ListToStringXStreamConverter(String alias) {
        super();
        this.alias = alias;
    }

    public boolean canConvert(Class type) {
        return true;
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {

        ArrayList<String> list = (ArrayList) source;

        for (String string : list) {
            writer.startNode(alias);
            writer.setValue(string);
            writer.endNode();
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        throw new UnsupportedOperationException();

    }
}
