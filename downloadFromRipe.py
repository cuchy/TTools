#!/usr/bin/python
import os
import sys

if(len(sys.argv) > 5):
# Call from TTools.py = python downloadFromRipe.py rrc00 2014-02-15 16:45 17:10 /home/
	router= sys.argv[1]
	full_date= sys.argv[2].replace("-","")
	from_time= sys.argv[3].replace(":","")
	to_time= sys.argv[4].replace(":","")
	destination=sys.argv[5]

	date=sys.argv[2].split("-")[0]+"."+sys.argv[2].split("-")[1]
	file_name="Traza_"+full_date+"-"+str(from_time)+"_"+str(to_time)
else:
	date="2014.02"
	full_date="20140215"
	from_time=1645
	to_time=1710
	router = raw_input('Nombre del router (ej: rrc00, rrc01): ')
	file_name= raw_input('Nombre del archivo ha crear: ')

i=int(from_time)
while i < int(to_time):
	if (i < 10):
		value="000"+str(i)
        elif (i < 100):
	 	value="00"+str(i)
	elif (i < 1000):
	 	value="0"+str(i)
	os.system("wget http://data.ris.ripe.net/"+router+"/"+date+"/updates."+full_date+"."+value+".gz")
	if (i == 0):
		os.system("bgpdump -M updates."+full_date+"."+value+".gz > "+file_name)
	else :
		os.system("bgpdump -M updates."+full_date+"."+value+".gz >> "+file_name)
	os.system("rm "+"updates."+full_date+"."+value+".gz")
	os.system("mv "+file_name+ " "+destination+"/"+file_name )
	i += 5
	if (i % 100)==60:
		i+=40

print ("Se creo el archivo "+file_name+" que contiene los updates BGP")

