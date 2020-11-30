package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class DataParser {

    private final HashMap<Integer,Integer> bid_map;
    private final HashMap<Integer,Integer> ask_map;


    private final DataWriter dataWriter;

    public DataParser() throws IOException{
        bid_map = new HashMap<>();
        ask_map = new HashMap<>();
        dataWriter = new DataWriter();
    }



    public void read() throws IOException{
        File file = new File("input.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while((line=reader.readLine())!=null){
            parseLine(line);
        }
        dataWriter.closeWriter();


    }

    private void parseLine(String query) throws IOException {
        String[] strings = query.split(",");
        switch (strings[0]){
            case "u": {
                int price = Integer.parseInt(strings[1]);
                int size = Integer.parseInt(strings[2]);

                if (strings[3].equals("bid")) {
                   if(bid_map.containsKey(price)){
                       bid_map.put(price,bid_map.get(price)+size);
                   }else
                       bid_map.put(price,size);

                } else if (strings[3].equals("ask")) {
                   if(ask_map.containsKey(price)){
                       ask_map.put(price,ask_map.get(price)+size);
                   }else
                       ask_map.put(price,size);
                }
            }
            case "q": {
                if (strings[1].equals("best_bid")) {
                    int bestPrice;
                    int b_size;
                    bestPrice = getBestBid();
                    b_size = bid_map.get(bestPrice);
                    dataWriter.write(bestPrice + "," + b_size);
                } else if (strings[1].equals("best_ask")) {
                    int bestPrice;
                    int b_size;
                    bestPrice = getBestAsk();
                    b_size = ask_map.get(bestPrice);
                    dataWriter.write(bestPrice + "," + b_size);

                } else if (strings[1].equals("size")) {
                    int price = Integer.parseInt(strings[2]);
                    if(bid_map.containsKey(price)){
                        dataWriter.write(bid_map.get(price)+"");
                    }
                    else if(ask_map.containsKey(price)){
                        dataWriter.write(ask_map.get(price)+"");
                    }
                }
            }
            case "o":{
                if(strings[1].equals("buy")){
                    int size = Integer.parseInt(strings[2]);
                    boolean flag = true;
                    do{
                        int price = getBestAsk();
                        if(size<ask_map.get(price)){
                            ask_map.put(price,ask_map.get(price)-size);
                            flag = false;
                        }else if(size==ask_map.get(price)){
                            ask_map.remove(price);
                            flag = false;
                        }else{
                            size-=ask_map.get(price);
                            ask_map.remove(price);
                        }
                    }while (flag);
                }
                else if(strings[1].equals("sell")){
                    int size =Integer.parseInt(strings[2]);
                    boolean flag = true;
                    do{
                        int price = getBestBid();
                        if(size<bid_map.get(price)){
                            bid_map.put(price,bid_map.get(price) -size);
                            flag =false;
                        }else if(size==bid_map.get(price)){
                            bid_map.remove(price);
                            flag = false;
                        }else{
                            size-=bid_map.get(price);
                            bid_map.remove(price);
                        }
                    }while(flag);
                }
            }
        }
    }


    private int getBestBid(){
        int currentBestPrice = 0;
        Set<Integer> priceSet = bid_map.keySet();
        for(Integer x:priceSet){
            if(x>currentBestPrice && bid_map.get(x)!=0 ){
                currentBestPrice = x;
            }
        }
        return currentBestPrice;
    }

    private int getBestAsk(){
        int currentBestAsk = Integer.MAX_VALUE;
        Set<Integer> priceSet = ask_map.keySet();
        for(Integer x:priceSet){
            if(x<currentBestAsk && ask_map.get(x)!=0){
                currentBestAsk = x;
            }
        }
        return currentBestAsk;
    }

}
