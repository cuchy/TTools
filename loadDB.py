#!/usr/bin/python
# -*- coding: utf-8 -*-

# Este script utiliza la herraminta bgpdump, por lo que es necesario tenerla instalada en el sistema.

# Luego de realizar una emulacion con el mininext, para poder estudiar de forma mas comoda los
# resultados obtenidos, se cargan en una base de datos "mysql".
# Este script carga la base con los datos que se encuntran en la carpeta /var/log/mininext/
# Dado que la ejecuccion se ejecuta con "sudo" es necesario cambiarle los permisos 
# a todos los archivos ubicados en la carpeta mininext.

# Dado la informacion de la tabla RIB_IN y la table TABLE_DUMP son generados en binario,
# este script hace la taducción con la ayuda de la herramineta bgpdump.

import MySQLdb
import warnings
import os
import os.path
import re
import sys

###### PARAMETROS DE LA BASE DE DATOS ########

HOST_NAME ="localhost"
USER_NAME ="root"
PASSWORD  ="toor"

##############################################
print "=========================================================================\n"
print "Este script carga la Base de Datos con los resultados obtenidos de la      "
print "ejecucion del Mininet, generando 4 tablas: 				  "
print "						  rib_in_X			  "   
print "	         		  		  loc_rib_ipv4_X	     	  "
print "	           				  loc_rib_ipv6_X	   	  "
print "	           			          table_dump_X		   	  "
print "donde X es la implementacion de BGP utilizada en la ejecucion\n\n	  "
print "	           UDELAR - Facultad de Ingenieria				  "
print "		     Proyecto de Grado - Año 2016				  "
print "		Autores: Viviana Solla & Gabriel Jambrina			  "
print "=========================================================================\n"

# 2 ways of invocation 
# "python GMLtoXMLconverte.py" or "python GMLtoXMLconverter.py + [mininext_path]+"
if(len(sys.argv) > 1):
	FILES_RUTE= sys.argv[1];
else:
	# Location of the RIB files and the TABLE_DUMP files by default
	FILES_RUTE= "/var/log/mininext"

if(len(sys.argv) > 3):
	DATABASE_NAME = sys.argv[2];
	BGP_TYPE = sys.argv[3];
else:
	print ('Ingrese los siguientes valores: ')
	DATABASE_NAME = raw_input('Nombre de la base: ')
	BGP_TYPE = raw_input('Implementacion de BGP (ej: FM, RR_SepD, RR_Sep): ')

# My AS Routers 
routers=os.listdir(FILES_RUTE)
pattern = re.compile("^(?!AS_.*)")

routers=filter(pattern.match, routers)

# Translate the binary file with all the bgp messages to a human readable file  
for rts in routers:
	os.system("bgpdump -M "+ FILES_RUTE + "/" +rts+"/quagga/RIB_IN >  "+ FILES_RUTE + "/" + rts+"/quagga/RIB_IN.1")

# Translate the binary file with the RIB table to a human readable file  
for rts in routers:
	if (os.path.isfile(FILES_RUTE + "/" +rts+"/quagga/TABLE_DUMP")):
		os.system("bgpdump -M "+ FILES_RUTE + "/" +rts+"/quagga/TABLE_DUMP >  "+ FILES_RUTE + "/" +rts +"/quagga/TABLE_DUMP.1")

# connect
db = MySQLdb.connect(host=HOST_NAME, user=USER_NAME, passwd=PASSWORD,  local_infile = 1)

cursor = db.cursor()
# CREATE THE DATABASE 
sql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME +";"
cursor.execute(sql)

# SELECT "abilene" DATABASE 
sql = "use " + DATABASE_NAME +";"
with warnings.catch_warnings():
	warnings.simplefilter('ignore')
	cursor.execute(sql)# will not warn


#Create ipls table
sql = """CREATE TABLE IF NOT EXISTS rib_in_"""+ BGP_TYPE +""" (
	versionBGP VARCHAR(10) NOT NULL,
	date VARCHAR(20) NOT NULL,
	mesgType VARCHAR(1) NOT NULL,
	ipSource VARCHAR(40) NOT NULL,
	localAS int(32),
	prefix VARCHAR(45),
	aspath VARCHAR(200),
	origin VARCHAR(10),
	router VARCHAR(10),
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY);"""
cursor.execute(sql)


#Create RIB-IN table
sql = """CREATE TABLE IF NOT EXISTS table_dump_"""+ BGP_TYPE +""" (
	tableDump VARCHAR(15) NOT NULL,
	date VARCHAR(20) NOT NULL,
	mesgType VARCHAR(1) NOT NULL,
	ipSource VARCHAR(40) NOT NULL,
	localAS int(32),
	prefix VARCHAR(45),
	aspath VARCHAR(200),
	origin VARCHAR(10),
	router VARCHAR(10),
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY);"""
cursor.execute(sql)

#Create Loc-RIB IPv4 table
sql = """CREATE TABLE IF NOT EXISTS loc_rib_ipv4_"""+ BGP_TYPE +""" (
	prefix VARCHAR(45),
	next_hope VARCHAR(40) NOT NULL,
	router VARCHAR(10) NOT NULL,
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY);"""
cursor.execute(sql)

#Create Loc-RIB IPv6 table
sql = """CREATE TABLE IF NOT EXISTS loc_rib_ipv6_"""+ BGP_TYPE +""" (
	prefix VARCHAR(45),
	next_hope VARCHAR(40) NOT NULL,
	router VARCHAR(10) NOT NULL,
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY);"""
cursor.execute(sql)


# There is a file for each router which contains the BGP update messages
# The location of this file is : /var/log/mininext/ROUTE-NAME/quagga/
for rt in routers:
	#load each file into the database under the table 'updatesMsj'
	ribFile=FILES_RUTE + "/" + rt + "/quagga/RIB_IN.1"

	sql ="""LOAD DATA LOCAL INFILE '%s'
		INTO TABLE %s
		FIELDS TERMINATED BY '|'
		LINES TERMINATED BY '\n'
		SET router= '%s';""" % (ribFile, "rib_in_"+BGP_TYPE, rt)

	print (' Cargando rib_in_'+ BGP_TYPE + ' del router '+rt+'...')
	#execute the sql function above1
	with warnings.catch_warnings():
    		warnings.simplefilter('ignore')
		cursor.execute(sql)# will not warn

	#load each file into the database under the table 'table_dump_'
	if (os.path.isfile(FILES_RUTE + "/" +rts+"/quagga/TABLE_DUMP")):	
		table_dump_File=FILES_RUTE + "/" + rt + "/quagga/TABLE_DUMP.1"

		sql ="""LOAD DATA LOCAL INFILE '%s'
			INTO TABLE %s
			FIELDS TERMINATED BY '|'
			LINES TERMINATED BY '\n'
			SET router= '%s';""" % (table_dump_File, "table_dump_"+BGP_TYPE, rt)

		print (' Cargando table_dump_'+ BGP_TYPE + ' del router '+rt+'...')
		#execute the sql function above
		with warnings.catch_warnings():
	    		warnings.simplefilter('ignore')
			cursor.execute(sql)# will not warn

	#load each file into the database under the table 'Loc-RIB_RR'
	loc_rib_File=FILES_RUTE + "/" + rt + "/quagga/Loc-RIB_ipv4"

	sql ="""LOAD DATA LOCAL INFILE '%s'
		INTO TABLE %s
		FIELDS TERMINATED BY '|'
		LINES TERMINATED BY '\n'
		SET router= '%s';""" % (loc_rib_File, "loc_rib_ipv4_"+BGP_TYPE, rt)

	print (' Cargando loc_rib_ipv4_'+ BGP_TYPE + ' del router '+rt+'...')
	#execute the sql function above
	with warnings.catch_warnings():
    		warnings.simplefilter('ignore')
		cursor.execute(sql)# will not warn

	#load each file into the database under the table 'Loc-RIB_RR'
	loc_rib_ipv6_File=FILES_RUTE + "/" + rt + "/quagga/Loc-RIB_ipv6"

	sql ="""LOAD DATA LOCAL INFILE '%s'
		INTO TABLE %s
		FIELDS TERMINATED BY '|'
		LINES TERMINATED BY '\n'
		SET router= '%s';""" % (loc_rib_ipv6_File, "loc_rib_ipv6_"+BGP_TYPE, rt)
	
	print (' Cargando loc_rib_ipv6_'+ BGP_TYPE + ' del router '+rt+'...')
	#execute the sql function above
	with warnings.catch_warnings():
    		warnings.simplefilter('ignore')
		cursor.execute(sql)# will not warn



#commit to the database
db.commit()

#close the database  
db.close()

print ('\nSe crearon las siguientes tablas en la base '+ DATABASE_NAME)
print (' rib_in_'+ BGP_TYPE)
print (' table_dump_'+ BGP_TYPE)
print (' loc_rib_ipv4_'+ BGP_TYPE) 
print (' loc_rib_ipv6_'+ BGP_TYPE +'\n') 



