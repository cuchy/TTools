#!/usr/bin/env python
# -*- coding: utf-8 -*-

# Convertidor de archivoGMLs GML (Proporcionados por la pagina "The topology zoo")  
# a XML para ser utilizados por la aplicaccion RRLoc

#Nota: El arhvivo .gml contiene informacion de los nodos internos al AS pero tambien
#      de los nodos externos ( o sea de otros AS) que se comunican con los routers 
#      de borde para intercambiar informacion de ruteo.
#      A nosotros nos intersea solamente los routers internos al AS y tambien nos intersea 
#      clasificar si es un router de borde o no, ya que el algoritmo optimal precisa de 
#      dicha informacion.

import os
import sys
import re

routersName = []
externalLinks= []
externalNodes= []
internalLinks= []

label = re.compile('\s*label.*')
longitude = re.compile('\s*Longitude.*')
latitude = re.compile('\s*Latitude.*')
internal = re.compile('\s*Internal.*')

source = re.compile('\s*source.*')
target = re.compile('\s*target.*')



if(len(sys.argv) > 1):
	print "========================================================================="
	print "Este script genera un archivo .xml para poder correr los algoritmos de   "
	print "localización de reflectores de rutas utilizando la herramienta RRLoc     "
	print "Al final de la ejecución del script se detallaran las conexión con otros "
	print "Sistemas Autonomos, información necesaria para poder realizar pruebas	"
	print "con el emulador con MiniNext						"
	print "	           UDELAR - Facultad de Ingeniería				"
	print "		     Proyecto de Grado - Año 2015				"
	print "		Autores: Viviana Solla & Gabriel Jambrina			"
	print "=========================================================================\n"
	if(len(sys.argv) > 2):
		ASN= sys.argv[2]
	else:
		ASN= raw_input('\nIngrese el Numero de Sistema Autonomo (ASN): ')
	if os.path.isfile(sys.argv[1]):
		
		dirActual = os.getcwd()
		nombre = sys.argv[1]
		nombre = nombre.split(".")[0]
		
		#Abro el archivo GML
		archivoGML = open(sys.argv[1],'r')
		#Creo el archvio XML
		archivoXML = open(nombre+'.xml','w')

		archivoXML.write('<?xml version="1.0" encoding="UTF-8" standalone="yes"?>\n<domain ASID="'+ASN+'">\n    <info>\n        <title>'+nombre+'</title>\n        <date>2015-10-15-00:00</date>\n        <author>Viviana Solla and Gabriel Jambrina</author>\n        <description/>\n        <units>\n            <unit type="bandwidth" value="mbps"/>\n            <unit type="delay" value="ms"/>\n        </units>\n        <diff-serv>\n            <priority ct="0" id="0" preemption="0"/>\n        </diff-serv>\n    </info>\n    <topology>\n        <nodes>\n')

		#Obtengo los nodos del archivo GML		
		linea = archivoGML.readline()

		while not ("node [" in linea):
			linea = archivoGML.readline()

		loopBackIP=0
		noneNum=1
		node=0
		# En el siguiente bucle recorro el archivo .gml de forma de obtener todos los nodos y
		# la informacion necesaria de cada uno de estos: label (contiene el nombre), latitud, longitud y 
		# si es un router interno 
		# Un vez que encunetro una linea que contenga el string "edge [" se termina el bucle
		while not ("edge [" in linea):
			linea = archivoGML.readline()
			if not ("node [" in linea) and not ("edge [" in linea) :
				if label.match(linea):
					id=re.sub('\s*label\s\"(.*)\"\n', r'\1', linea)
				elif latitude.match(linea):
					lati=re.sub('\s*Latitude\s(.*)\n', r'\1', linea)
				elif longitude.match(linea):
					longi=re.sub('\s*Longitude\s(.*)\n', r'\1', linea)
				elif internal.match(linea):
					inter=re.sub('\s*Internal\s(.*)\n', r'\1', linea)

				
			else :	
				# Cuando termino de obtener la informacion de un nodo, hago la traduccion
				# a xml.
				#Anters me fijo si es un nodos interno "inter==1" si no lo es, lo guardo en 
				#una lista porque es la manera de obtener los routers de borde de mi topologia
				if (inter=="1"):	
					id= id.replace(' ', '')
					id= id.replace('-', '_')
					id= id.replace(',', '')
					id= id.replace("'", "")
					id= id[0:10]
					if (id=="None"):
						id+=str(noneNum)
						noneNum+=1
					routersName.insert(node,id)
					#Agrego al nodo en el archivoGML
					archivoXML.write('            <node id="'+id+'">\n')
					archivoXML.write('                <rid>192.168.0.'+str(loopBackIP)+'</rid>\n                <type>CORE</type>\n')
					archivoXML.write('                <location latitude="-'+lati+'" longitude="'+longi+'"/>\n')
					archivoXML.write('            </node>\n')
					loopBackIP+=1
				else:
					routersName.insert(node, "external")
					externalNodes.append(node)
				node+=1
		archivoXML.write('        </nodes>\n')
		archivoXML.write('        <links>\n')
		
		extLinks=0
		
		#Luego de traducir todos los nodos pasamos a traducir los enlaces.
		#Los enlaces entre un router interno al AS y uno externo no son tomados 
		#en cuenta. Pero lo que si nos internesa de estos enlaces son los nodos
		#internos que forman el enlace ya que estos son los routers de borde de
		#nuestra topología.
		while (linea):
			linea = archivoGML.readline()
			if  not ("]" in linea):
				if source.match(linea):
					src=linea.split(" ")[5]
					src=src.split("\n")[0]
				elif target.match(linea):
					dest=re.sub('\s*target\s(.*)\n', r'\1', linea)
			else:
				if (routersName[int(src)]!="external" and routersName[int(dest)]!="external" ) :
					#Verifico que no se reptita el enlace ya que el totem no soporta multiples 
					#enlaces entre dos routers
					if not (src+'-'+dest in internalLinks) and not (dest+'-'+src in internalLinks) :
						internalLinks += [src+'-'+dest];					
						archivoXML.write('            <link id="'+routersName[int(src)]+'-'+routersName[int(dest)]+'">\n')
						archivoXML.write('                <from node="'+routersName[int(src)] +'"/>\n')
						archivoXML.write('                <to node="'+routersName[int(dest)] +'"/>\n')
						archivoXML.write('            </link>\n')

						archivoXML.write('            <link id="'+routersName[int(dest)]+'-'+routersName[int(src)]+'">\n')
						archivoXML.write('                <from node="'+routersName[int(dest)] +'"/>\n')
						archivoXML.write('                <to node="'+routersName[int(src)] +'"/>\n')
						archivoXML.write('            </link>\n')
				elif (routersName[int(src)]=="external"):
					extLinks+=1
					externalLinks.insert(extLinks, dest+'-'+src)
				elif (routersName[int(dest)]=="external"):
					extLinks+=1
					externalLinks.insert(extLinks, src+'-'+dest)

		archivoXML.write('        </links>\n')
		archivoXML.write('    </topology>\n') 
		archivoXML.write('    <igp>\n')
		archivoXML.write('        <links>\n')


		for link in internalLinks:
			src=link.split("-")[0]
			dest=link.split("-")[1]
			for k in [1,2]:
				archivoXML.write('	    <link id="'+routersName[int(src)]+'-'+routersName[int(dest)]+'">\n')
				archivoXML.write('		<static>\n')
				archivoXML.write('		    <metric>10.0</metric>\n')
				archivoXML.write('		    <te-metric>1.0</te-metric>\n')
				archivoXML.write('	    	    <mrbw>155000.0</mrbw>\n')
				archivoXML.write('		    <mbw>155000.0</mbw>\n')
				archivoXML.write('		</static>\n')
				archivoXML.write('	    </link>\n')
				tmp=dest;
				dest=src;
				src=tmp;
			

		archivoXML.write('        </links>\n')
		archivoXML.write('    </igp>\n')
		archivoXML.write('</domain>\n')
		archivoXML.close()

		#A continuacion necesito etiquetar los nodos de borde como "EDGE"
		#Al final imprimo los router de borde de manera de informar al usuario
		#donde se deben inyectar la trazas BGP
		if (extLinks !=0):
			print "En esta toplogía hay "+ str(extLinks) +" enlaces externos con otros Sistemas Autonomos\n"
			i=0		
			for extNode in externalNodes:
				showExtNodes=""
				for link in externalLinks:
					aux=link.split('-')
					if (str(externalNodes[i]) == aux[1]):			
						showExtNodes+= routersName[int(aux[0])]+" "
						# Busco los routers de borde y los clasificos como "EDGE"
						contents=open(nombre+'.xml').read()
						contents=re.sub(r'(<node id="'+routersName[int(aux[0])]+'">\n.*\n.*)<type>CORE</type>', r'\1<type>EDGE</type>', contents)
						archivoXML=open(nombre+'.xml', 'w')
                				archivoXML.write(contents)
              					archivoXML.flush()
                				archivoXML.close()	

				i+=1
				print "Los nodos que mantienen conexiones con AS"+str(i)+" son: "+showExtNodes
		print "\nSe ha generado el archivo "+sys.argv[1].split('.')[0]+".xml con exito"
	else:
		print "No se encuentra el archivoGML "+sys.argv[1]+"\n"

else:
		print "Debe ingresar el archivoGML .gml como parametro de entrada\n"


