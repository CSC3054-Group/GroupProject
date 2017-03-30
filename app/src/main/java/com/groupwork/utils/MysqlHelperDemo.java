package com.groupwork.utils;

/**
 * Created by LL on 2017/3/25.
 */

public class MysqlHelperDemo {

    public void MysqlHelperUsage(){

        MysqlHelper ms = new MysqlHelper("54.154.229.112", "RFinder");
        //1st argument: the ip address of mysql.
        //2nd argument: name of database
        /* the ip address is not fix. When you need to get access to cloud database you should first login to the amazon ec2 service:
           https://aws.amazon.com
           and start the instance. Once you start you will find the IP address of it.
           After testing please stop the instance because there will be a charge if we exceed the free time limit.
        */

        //example of sql statement
		String sql_create = "create table test("
				+ "name varchar(50) not null,"
				+ "pwd varchar(50) not null)";
        String sql_drop = "drop table test";
        String sql_insert = "insert into user values('hello', 'world')";
        String sql_delete = "delete from user where username='hello'";
        String sql_update = "update user set password='55555' where username='admin2'";

        ms.execute(sql_create);

        try{
            if(ms.rs != null){
                //ms.rs is the result of excution
                //if there is no result ms.rs is null
                while(ms.rs.next()){
                    String column_name = "username";
                    String value = ms.rs.getString(column_name);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{}
    }
}
