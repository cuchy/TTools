#!/usr/bin/python
# -*- coding: utf-8 -*-

import MySQLdb
import warnings
import os
import re
import sys

#La herramienta bgpdump hace la traduccion de los archivos RIB_IN y TABLE_DUMP manteniendo 
#dos copias de la misma infomación, una en binario y otra en texto plano
#Este script borra la copia en texto plano para libera espacio en el disco.

#En caso de que la carpeta mininext no este ubicada en el lugar por defecto,
#se puede invocar el script de la forma "python BorrarArhvios.py path_to_mininext"

if(len(sys.argv) > 1):
	FILES_RUTE= sys.argv[1];
else:
	# Ubicacion por defecto de la carpeta mininext
	FILES_RUTE= "/var/log/mininext"

# My AS Routers 
routers=os.listdir(FILES_RUTE)
pattern = re.compile("^(?!AS_.*)")

routers=filter(pattern.match, routers)

# Translate the binary file with all the bgp messages to a human readable file  
for rts in routers:
	print "Borrando RIB_IN.1 y TABLE_DUMP.1 del router "+ rts + " ...\n"
	os.system("rm "+ FILES_RUTE + "/" + rts+"/quagga/RIB_IN.1")
	os.system("rm "+ FILES_RUTE + "/" +rts +"/quagga/TABLE_DUMP.1")

print "Archivos borrados con exito\n"
