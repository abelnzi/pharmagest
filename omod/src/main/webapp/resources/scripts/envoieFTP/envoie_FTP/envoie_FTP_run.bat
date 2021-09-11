%~d0
cd %~dp0
java -Xms256M -Xmx1024M -cp .;../lib/routines.jar;../lib/log4j-1.2.16.jar;../lib/dom4j-1.6.1.jar;../lib/talendcsv.jar;../lib/jsch-0.1.51.jar;envoie_ftp_0_1.jar; etl_restor.envoie_ftp_0_1.envoie_FTP --context=prod %* 