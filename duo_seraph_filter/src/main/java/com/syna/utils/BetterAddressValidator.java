package com.syna.utils;

import org.apache.commons.net.util.SubnetUtils;

/**
 * Created by tlchang on 1/11/2016.
 */
public class BetterAddressValidator {

        public static void main(String [] args){
            String[] whiteList = {"10.0.0.0/8", "172.16.0.0/16", "192.168.11.1"};
            String[] blackList = {"192.168.11.1"};

            BetterAddressValidator whiteListValidator = new BetterAddressValidator( whiteList);
            BetterAddressValidator blackListValidator = new BetterAddressValidator( blackList);
            System.out.println("true : " + whiteListValidator.isInRange("10.1.0.10"));

            System.out.println("true : " + whiteListValidator.isInRange("10.128.0.10"));

            System.out.println("true : " + whiteListValidator.isInRange("10.14.0.24"));

            System.out.println("true : " + whiteListValidator.isInRange("172.16.1.1"));

            System.out.println("true : " + whiteListValidator.isInRange("172.16.16.15"));

            System.out.println("true : " + whiteListValidator.isInRange("192.168.11.1"));

            System.out.println("false : " + whiteListValidator.isInRange("11.1.0.10"));
            System.out.println("false : " + whiteListValidator.isInRange("64.13.16.238"));
            System.out.println("false : " + whiteListValidator.isInRange("172.15.1.1"));
            System.out.println("false : " + whiteListValidator.isInRange("192.168.11.2"));

            String remoteAddr;

            remoteAddr = "10.1.0.10";
            if (blackListValidator.isInRange(remoteAddr)) {
                System.out.println("Black is wrong");
            }
            else {
                if (whiteListValidator.isInRange(remoteAddr))
                    System.out.println("White is correct");
                else
                    System.out.println("None is wrong");
            }

            remoteAddr = "172.16.1.1";
            if (blackListValidator.isInRange(remoteAddr)) {
                System.out.println("Black is wrong");
            }
            else {
                if (whiteListValidator.isInRange(remoteAddr))
                    System.out.println("White is correct");
                else
                    System.out.println("None is wrong");
            }

            remoteAddr = "192.168.11.1";
            if (blackListValidator.isInRange(remoteAddr))
                System.out.println("Black is right");
            else {
                if (whiteListValidator.isInRange(remoteAddr))
                    System.out.println("White is wrong");
                else
                    System.out.println("None is wrong");
            }

            remoteAddr = "192.168.11.2";
            if (blackListValidator.isInRange(remoteAddr))
                System.out.println("Black is wrong");
            else {
                if (whiteListValidator.isInRange(remoteAddr))
                    System.out.println("White is wrong");
                else
                    System.out.println("None is right");
            }


        }

        private SubnetUtils[] approvedAddressList = null;

        public BetterAddressValidator(
                String[] approvedAddressList) {
            this.approvedAddressList =
                    new SubnetUtils[approvedAddressList.length];
            for(int i = 0; i < approvedAddressList.length; i++) {
                if(approvedAddressList[i].indexOf("/") > 0) {
                    this.approvedAddressList[i] =
                            new SubnetUtils(approvedAddressList[i]);
                } else {
                    this.approvedAddressList[i] =
                            new SubnetUtils(approvedAddressList[i]
                                    + "/32");
                }
                this.approvedAddressList[i].setInclusiveHostCount(true);
            }
        }

        public boolean isInRange(String ipAddress) {
            for(SubnetUtils approvedAddress : approvedAddressList) {
                if(approvedAddress.getInfo().isInRange(ipAddress)) {
                    return true;
                }
            }
            return false;
        }
}
