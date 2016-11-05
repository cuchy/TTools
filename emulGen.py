#!/usr/bin/env python

import os
import sys

routersName = []
routersID = []
routersInyectores = []
routersInyectoresAS = []
routersLinks = []

if(len(sys.argv) > 1):
	# Veo si se esta llamando solo con la topologia o si me pasan los datos tambien
	if(len(sys.argv)>2):
		tengoDatos = True;
	else:
		tengoDatos = False;

	if os.path.isfile(sys.argv[1]):
		dirActual = os.getcwd()
		
		# Se crea el directorio que contendra todos archivos necesarios para la simulacion en el directorio actual, bajo el nombre MiniNeXt_RR 
		nombre = os.path.basename(sys.argv[1])
		nombre = nombre.split(".")
		dirNuevo = os.path.join(os.curdir, 'MiniNeXt_' + nombre[0])
		if not os.path.isdir(dirNuevo):
			os.mkdir(dirNuevo)
		#os.chdir(dirNuevo)


		# Abro el archivo para obtener todos los routers de la topologia
		archivo = open(sys.argv[1],'r')
		
		linea = archivo.readline()
		while not ("<topology>" in linea):
			linea = archivo.readline()

		while not ("</nodes>" in linea):
			if ("<node id=" in linea):
				auxLinea = linea.split("\"")
				r = auxLinea[1]
				r = '_'.join(r.split())
				if not r in routersName:
					routersName.append(r)
			
			linea = archivo.readline()

		if not tengoDatos:
			print "\nNumero de routers inyectores que se van a generar:"
			cantRoutersInyectores = input()
			print "\nLos routers con los que se pueden establecer las sesiones eBGP son:"
			for i in routersName:
				print i

			i = 0
			while (i < cantRoutersInyectores):
				print "\nEscriba el nombre del router que tendra una sesion eBGP con el router AS_" + str(i + 1)
				r = raw_input()
				if (r in routersName):
					routersInyectores.append(r)
					i = i + 1
				else:
					"El router especificado no existe!"
					print "\nLos routers con los que se pueden establecer las sesiones eBGP son:"
					for l in routersName:
						print l
		else:
			# Obtengo los datos que me pasan al llamar a la funcion
			routersInyectores = sys.argv[2].split(",")
			cantRoutersInyectores = len(routersInyectores); 
			for l in routersInyectores:
				print l
			

		while not ("<links>" in linea):
			linea = archivo.readline()

		for r in routersName:
			routersLinks.append(0)

		while not ("</links>" in linea):
			if ("<link id=" in linea):
				auxLinea = linea.split("\"")
				auxLinea = auxLinea[1].split("-")
				auxLinea = '_'.join(auxLinea[0].split())
				auxiliar = routersName.index(auxLinea)
				routersLinks[auxiliar] = routersLinks[auxiliar] + 1
			linea = archivo.readline()

		# Cierro el archivo		
		archivo.close()


		# Abro el archivo para procesarlo
		archivo = open(sys.argv[1],'r')

		# Creo la carpeta configs
		os.chdir(dirNuevo)
		dirConfigs = os.path.join(os.curdir, 'configs')
		if not os.path.isdir(dirConfigs):
			os.mkdir(dirConfigs)
		os.chdir(dirActual)

		linea = archivo.readline()
		while not ("ASID" in linea):
			linea = archivo.readline()

		# Obtengo el ASID
		auxLinea = linea.split(" ")
		auxLinea = auxLinea[1].split("\"")
		ASID = auxLinea[1]


		if not tengoDatos:
		# Pido los numeros de AS de los routers inyectores
			i = 0 
			while(i < cantRoutersInyectores):
				print "\nEscriba el numero de Sistema Autonomo (AS) del router AS_" + str(i + 1)
				ASN = input()
				if (ASN != int(ASID)):
					routersInyectoresAS.append(ASN)
					i = i + 1
				else:
					print "El numero de Sistema Autonomo debe ser diferente del que tiene asignado la topologia"
		else:
			# Obtengo los ASN de los inyectores de los datos que me pasan en la funcion
			routersInyectoresAS = sys.argv[3].split(",")
			for l in routersInyectoresAS:
				print l
			


		# Me ubico en el tag <topology> del archivo
		while not ("<topology>" in linea):
			linea = archivo.readline()

		# Entro en el tag <nodes>
		while not ("<nodes>" in linea):
			linea = archivo.readline()

		# Creo el archivo topo.py
		os.chdir(dirNuevo)
		archivoTopo = open('topo.py','w')
		archivoTopo.write("import inspect\nimport os\nfrom mininext.topo import Topo\nfrom mininext.services.quagga import QuaggaService\n\nfrom collections import namedtuple\n\nQuaggaHost = namedtuple(\"QuaggaHost\", \"name ip loIP\")\nnet = None\n\n\nclass QuaggaTopo(Topo):\n\n    \"Creates a topology of Quagga routers\"\n\n    def __init__(self):\n        \"\"\"Initialize a Quagga topology with 12 routers, configure their IP\n           addresses, loop back interfaces, and paths to their private\n           configuration directories.\"\"\"\n        Topo.__init__(self)\n\n        # Directory where this file / script is located\n        selfPath = os.path.dirname(os.path.abspath(\n            inspect.getfile(inspect.currentframe())))  # script directory\n\n        # Initialize a service helper for Quagga with default options\n        quaggaSvc = QuaggaService(autoStop=False)\n\n        # Path configurations for mounts\n        quaggaBaseConfigPath = selfPath + '/configs/'\n\n        # List of Quagga host configs\n        quaggaHosts = []\n\n")

		os.chdir(dirActual)

		# Creo las carpetas y archivos del router
		linea = archivo.readline()
		while not ("</nodes>" in linea):
			# Creo la carpeta del router
			auxLinea = linea.split("\"")
			router = auxLinea[1]
			router = '_'.join(router.split())

			os.chdir(dirNuevo + '/' + dirConfigs)
			carpetaNueva = os.path.join(os.curdir, router)
			if not os.path.isdir(carpetaNueva):
				os.mkdir(carpetaNueva)

			#Creo los archivos
			os.chdir(carpetaNueva)

			# Archivo deamons
			archivoNuevo = open('daemons','w')
			archivoNuevo.write("zebra=yes\nbgpd=yes\nospfd=yes\nospf6d=no\nripd=yes\nripngd=no\nisisd=no")
			archivoNuevo.close()

			# Archivo debian.conf
			archivoNuevo = open('debian.conf','w')
			archivoNuevo.write("vtysh_enable=yes\nzebra_options=\" --daemon -A 127.0.0.1\"\nbgpd_options=\"  --daemon -A 127.0.0.1\"\nospfd_options=\" --daemon -A 127.0.0.1\"\nospf6d_options=\"--daemon -A ::1\"\nripd_options=\"  --daemon -A 127.0.0.1\"\nripngd_options=\"--daemon -A ::1\"\nisisd_options=\" --daemon -A 127.0.0.1\"")
			archivoNuevo.close()
		
			# Busco el router ID (rid)
			while not ("<rid>" in linea):
				linea = archivo.readline()

			auxLinea = linea.split(">")
			auxLinea = auxLinea[1].split("<")

			# Le sumo 1 al RID
			RID = auxLinea[0]
			aux = RID.split(".")
			aux[3]=int(aux[3])+1
			RID = aux[0]+"."+aux[1]+"."+aux[2]+"."+str(aux[3])

			if not RID in routersID:
				routersID.append(RID)

			# Creo el archivo bgpd.conf
			archivoNuevo = open('bgpd.conf','w')
			archivoNuevo.write("!\npassword pass01\nlog file /var/log/quagga/bgpd.log\n!\ndump bgp updates /var/log/quagga/RIB_IN\ndump bgp routes-mrt /var/log/quagga/TABLE_DUMP 5m\n!\nline vty\n!\nrouter bgp " + ASID + "\n bgp router-id " + RID + "\n")
			# Si tiene un router inyector de peer, lo agrego como neighbor
			k = 0
			while (k < cantRoutersInyectores) :
				if (routersInyectores[k] == router):
					inyector = k
					inyector = inyector + 1
					archivoNuevo.write(" neighbor 172.16." + str(inyector) + ".2 remote-as " + str(routersInyectoresAS[inyector - 1]) + "\n neighbor 172.16." + str(inyector) + ".2 soft-reconfiguration inbound\n")
				k = k + 1
			archivoNuevo.close()

			# Agrego el router al archivo topo.py
			archivoTopo.write("        r_" + router + "=self.addHost(name='" + router + "', ip='" + RID + "', hostname='" + router + "', privateLogDir=True, privateRunDir=True, inMountNamespace=True, inPIDNamespace=True, inUTSNamespace=True)\n")

			# Agrego las interfaces
			archivoNuevo = open('zebra.conf','w')
			archivoNuevo.write("!\n! Zebra configuration saved from vty\n!\n!\n!\nip forwarding\n!\n!\nline vty\n!\ninterface lo\n ip address " + RID + "/32\n!\n")

			archivoNuevo2 = open('ospfd.conf','w')
			archivoNuevo2.write("!\nline vty\n!\ninterface lo\n!\n")

			cantInterfaces = routersLinks[routersName.index(router)]

			i=0
			while (i < cantInterfaces):
				archivoNuevo.write("interface " + router + "-eth" + str(i) + "\n no ipv6 nd suppress-ra\n!\n")
				archivoNuevo2.write("interface " + router + "-eth" + str(i) + "\n!\n")
				i = i + 1

			# Si tiene un router inyector de peer, le agrego la interfaz
			k = 0
			while (k < cantRoutersInyectores):
				if (router == routersInyectores[k]):
					archivoNuevo.write("interface " + router + "-eth" + str(i) + "\n no ipv6 nd suppress-ra\n ipv6 address fc00::" + str(hex(k+1).split('x')[1]) + ":8/127\n!\n")
					i = i + 1
				k = k + 1

			archivoNuevo.close()

			archivoNuevo2.write("router ospf\n ospf router-id " + RID + "\n network " + RID + "/32 area 0.0.0.0\n")
			archivoNuevo2.close()

			while not ("</node>" in linea):
				linea = archivo.readline()

			os.chdir(dirActual)
			linea = archivo.readline()

		# Agrego las carpetas y archivos de configuracion de los routers inyectores
		k = 0		
		while (k < cantRoutersInyectores):
			os.chdir(dirNuevo + '/' + dirConfigs)
			indice = k
			indice = indice + 1
			carpetaNueva = os.path.join(os.curdir, "AS_" + str(indice))
			if not os.path.isdir(carpetaNueva):
				os.mkdir(carpetaNueva)
			k = k + 1
			
			# Creo los archivos
			os.chdir(carpetaNueva)
            
			# Archivo debian.conf
			archivoNuevo = open('debian.conf','w')
			archivoNuevo.write("vtysh_enable=yes\nzebra_options=\" --daemon -A 127.0.0.1\"\nbgpd_options=\"  --daemon -A 127.0.0.1\"\nospfd_options=\" --daemon -A 127.0.0.1\"\nospf6d_options=\"--daemon -A ::1\"\nripd_options=\"  --daemon -A 127.0.0.1\"\nripngd_options=\"--daemon -A ::1\"\nisisd_options=\" --daemon -A 127.0.0.1\"")
			archivoNuevo.close()

			# Archivo daemons
			archivoNuevo = open('daemons','w')
			archivoNuevo.write("zebra=yes\nbgpd=no\nospfd=no\nospf6d=no\nripd=no\nripngd=no\nisisd=no")
			archivoNuevo.close()

			# Archivo zebra.conf
			archivoNuevo = open('zebra.conf', 'w')
			archivoNuevo.write("!\n! Zebra configuration saved from vty\n!\n!\n!\nip forwarding\n!\n!\nline vty\n!\ninterface AS_" + str(indice) + "-eth0\n ip address 172.16." + str(indice) + ".2/30\n no ipv6 nd suppress-ra\n!\n")
			archivoNuevo.close()

			os.chdir(dirActual)

			# Voy al directorio raiz 
			os.chdir(dirNuevo)
			# Escribo el archivo topo.py
			archivoTopo.write("        r_AS_" + str(indice) + "=self.addHost(name='AS_" + str(indice) + "', ip='172.16." + str(indice) + ".2', hostname='AS_" + str(indice) + "', privateLogDir=True, privateRunDir=True, inMountNamespace=True, inPIDNamespace=True, inUTSNamespace=True)\n")

			os.chdir(dirActual)

		while not ("<links>" in linea):
			linea = archivo.readline()

		# topo.py
		archivoTopo.write("\n\n        # Add links\n")

		# Se agregan los links y las IPs de las interfaces
		links = []
		j = 0

		linea = archivo.readline()
		while not ("</links>" in linea):
			while not ("<link id=" in linea):
				linea = archivo.readline()

			link = linea.split("\"")
			link = link[1].split("-")
			r1 = '_'.join(link[0].split())
			r2 = '_'.join(link[1].split())

			l1 = r1 + "-" + r2
			l2 = r2 + "-" + r1

			if not ((l1 in links) or (l2 in links)):
				links.append(r1 + "-" + r2)
				os.chdir(dirNuevo + '/' + dirConfigs + "/" +r1)
				archivoNuevo = open('ospfd.conf','a')
				archivoNuevo.write(" network 10.0." + str(j) + ".1/30 area 0.0.0.0\n")
				archivoNuevo.close()
				os.chdir(dirActual)
				os.chdir(dirNuevo + '/' + dirConfigs + "/" + r2)
				archivoNuevo = open('ospfd.conf','a')
				archivoNuevo.write(" network 10.0." + str(j) + ".2/30 area 0.0.0.0\n")
				archivoNuevo.close()
				os.chdir(dirActual)
				j = j + 1
		
				# Agrego el link en el archivo topo.py
				archivoTopo.write("        self.addLink(r_" + r1 + ", r_" + r2 + ")\n")

			while not ("</link>" in linea):
				linea = archivo.readline()

			linea = archivo.readline() 

		# Se agregan los links de los routers inyectores al archivo topo.py
		k = 0
		while (k < cantRoutersInyectores):
			indice = k
			indice = indice + 1
			archivoTopo.write("        self.addLink(r_AS_" + str(indice) + ", r_" + routersInyectores[k] + ")\n")
			k = k + 1

		# Se agregan los neighbors en los archivos bgpd.conf
		while not ("<bgp>" in linea):
			linea = archivo.readline()

		while not ("<routers>" in linea):
			linea = archivo.readline()

		linea = archivo.readline()
		while not ("</routers>" in linea):
	
			lineaAuxiliar = linea

			auxSplit = linea.split("\"")
			router = '_'.join(auxSplit[1].split())

			# Abro el archivo bgpd.conf para agregarle los neighbors
			os.chdir(dirNuevo + '/' + dirConfigs + "/" + router)
			archivoNuevo = open('bgpd.conf','a')

			while not ("<neighbors>" in linea):
				linea = archivo.readline()

			linea = archivo.readline()

			if ("reflector=\"true\"" in lineaAuxiliar):
				i = 1
				while not ("</neighbors>" in linea):
					varAux = linea

					linea = linea.split("\"")
					# Le sumo 1 a la IP del neighbor, ya que asi lo agregamos anteriormente
					IPneighbor = linea[3]			
					aux = IPneighbor.split(".")
					aux[3]=int(aux[3])+1
					IPneighbor = aux[0]+"."+aux[1]+"."+aux[2]+"."+str(aux[3])
					
					if ("reflector-client=\"true\"" in varAux):
						archivoNuevo.write(" neighbor " + IPneighbor + " remote-as " + linea[1] + "\n neighbor " + IPneighbor + " update-source lo\n")

					else:
						archivoNuevo.write(" neighbor " + IPneighbor + " remote-as " + linea[1] + "\n neighbor " + IPneighbor + " description client" + str(i) + "\n neighbor " + IPneighbor + " route-reflector-client\n neighbor " + IPneighbor + " update-source lo\n")

					if router in routersInyectores:
						archivoNuevo.write(" neighbor " + IPneighbor + " next-hop-self\n")
			
					i = i + 1
					linea = archivo.readline()
			else:
				while not ("</neighbors>" in linea):
					linea = linea.split("\"")
					# Le sumo 1 a la IP del neighbor, ya que asi lo agregamos anteriormente
					IPneighbor = linea[3]			
					aux = IPneighbor.split(".")
					aux[3]=int(aux[3])+1
					IPneighbor = aux[0]+"."+aux[1]+"."+aux[2]+"."+str(aux[3])
					archivoNuevo.write(" neighbor " + IPneighbor + " remote-as " + linea[1] + "\n neighbor " + IPneighbor + " update-source lo\n")
					if router in routersInyectores:
						archivoNuevo.write(" neighbor " + IPneighbor + " next-hop-self\n")
					linea = archivo.readline()
		
			archivoNuevo.close()
			os.chdir(dirActual)
	
			while not ("</router>" in linea):
				linea = archivo.readline()
			linea = archivo.readline()

		# Lista de hosts en el archivo topo.py
		archivoTopo.write("\n        lstHosts= ['" + routersName[0] + "'")
		for i in range(1, len(routersName)):
			archivoTopo.write(", '" + routersName[i] + "'")
		k = 0
		while (k < cantRoutersInyectores):
			indice = k
			indice = indice + 1
			archivoTopo.write(", 'AS_" + str(indice) + "'")
			k = k + 1
		archivoTopo.write("]\n\n        for host in lstHosts:\n            # Configure and setup the Quagga service for this node\n            quaggaSvcConfig = \\\n                {'quaggaConfigPath': quaggaBaseConfigPath + host}\n            self.addNodeService(node=host, service=quaggaSvc, nodeConfig=quaggaSvcConfig)")


		# Creo el archivo start.py
		os.chdir(dirNuevo)
		archivoStart = open('start.py','w')
		archivoStart.write("#!/usr/bin/python\n\n\n\nimport sys\nimport atexit\nimport re\n\n# patch isShellBuiltin\nimport mininet.util\nimport mininext.util\nmininet.util.isShellBuiltin = mininext.util.isShellBuiltin\nsys.modules['mininet.util'] = mininet.util\n\nfrom mininet.util import dumpNodeConnections\nfrom mininet.node import OVSController\nfrom mininet.log import setLogLevel, info\n\nfrom mininext.cli import CLI\nfrom mininext.net import MiniNExT\n\nfrom topo import QuaggaTopo\n\nnet = None\n\n\ndef startNetwork():\n    \"instantiates a topo, then starts the network and prints debug information\"\n\n    info('** Creating Quagga network topology\\n')\n    topo = QuaggaTopo()\n\n    info('** Starting the network\\n')\n    global net\n    net = MiniNExT(topo, controller=OVSController)\n    net.start()\n    addRouterInterfaces(net)\n\n    info('** Dumping host connections\\n')\n    dumpNodeConnections(net.hosts)\n\n    info('** Running CLI\\n')\n    ##############################\n    ####      FIRST STAGE      ###\n    ##############################\n    CLI(net)\n\n    ##############################\n    ####     SECOND STAGE     ####\n    ##############################\n\n\n    # Injecting BGP Updates from external routers\n    # Open a new xterm terminal for every external router\n    for host in net.hosts:\n        m = re.search(\"^AS_(.*)\", host.name)\n        if m :\n            host.cmd('xterm -e exabgp archivo-'+m.group(1)+'.conf &')\n\n\n    ##############################\n    ####     THIRD STAGE     ####\n    ##############################\n    CLI(net)\n\n    ##############################\n    ####     FOURTH STAGE     ####\n    ##############################\n\n    #Save the FIB in a File named FIB.txt\n    for host in net.hosts:\n        m = re.search(\"^(?!AS_.*)\", host.name)\n        if m :\n            host.cmdPrint('vtysh -c \"show ip route\" | sed \"1,5d\" |  sed \"/^[^B]/d\" | awk \\'{ print $2\"|\"$5}\\' | sed \"s/,//\" > /var/log/quagga/Loc-RIB_ipv4')\n            host.cmdPrint('vtysh -c \"show ipv6 route\" | sed \"1,5d\" |  sed \"/^[^B]/d\" | awk \\'{ print $2\"|\"$5}\\' | sed \"s/,//\" > /var/log/quagga/Loc-RIB_ipv6')\n\n\n\ndef stopNetwork():\n    \"stops a network (only called on a forced cleanup)\"\n\n    if net is not None:\n        info('** Tearing down Quagga network\\n')\n        net.stop()\n\ndef addRouterInterfaces( net ):\n    hosts=net.hosts\n    print \"Configuring Every Interface\\n\\n\"\n    for host in hosts:\n        print \"Host name: \", host.name\n")

		os.chdir(dirActual)

		for host in routersName:
			archivoStart.write("        if host.name=='" + host + "':\n")
			os.chdir(dirNuevo+ '/' + dirConfigs + "/" + host)
			f = open('ospfd.conf','r')

			zebra = open('zebra.conf','r')
			contenido = zebra.readlines()
			zebra.close()

			interfaz = 0

			for line in f:
				if "network 10.0" in line:
					aux = line.split(" ")
					aux = aux[2].split("/")
					indice = contenido.index("interface "+ host + "-eth" + str(interfaz) + "\n")
					contenido.insert(indice + 1, " ip address " + str(aux[0]) + "/30\n")
					interfaz = interfaz + 1

			# Si tengo como peer un router inyector lo agrego al archivo start.py
			k = 0
			while (k < cantRoutersInyectores):
				if (host == routersInyectores[k]):
					indiceAux = k
					indiceAux = indiceAux + 1
					indice = contenido.index("interface "+ host + "-eth" + str(interfaz) + "\n")
					contenido.insert(indice + 1, " ip address 172.16." + str(indiceAux) + ".1/30\n")
					interfaz = interfaz + 1
				k = k + 1

			zebra = open('zebra.conf', 'w')
			contenido = "".join(contenido)
			zebra.write(contenido)
			zebra.close()

			archivoStart.write("            host.cmd('echo \"1\" > /proc/sys/net/ipv4/ip_forward')\n")
			f.close()
			os.chdir(dirActual)

		archivoStart.write("\n\n\n\nif __name__ == '__main__':\n    # Force cleanup on exit by registering a cleanup function\n    atexit.register(stopNetwork)\n\n    # Tell mininet to print useful information\n    setLogLevel('info')\n    startNetwork()")

		archivoStart.close()

		archivoTopo.close()
		archivo.close()

		# Creo los archivos archivo-?.conf y announce-routes-from_?.py para todos los inyectores
		os.chdir(dirNuevo)
		k = 0
		while (k < cantRoutersInyectores):
			indice = k
			indice = indice + 1

			# Archivo archivo-as.conf
			directorioArchivos = os.getcwd()
			archivoConf = open('archivo-' + str(indice) + '.conf','w')
			archivoConf.write("neighbor 172.16." + str(indice) + ".1 {\n    router-id 195.66.225." + str(indice) + ";\n    local-address 172.16." + str(indice) + ".2;\n    local-as " + str(routersInyectoresAS[indice - 1]) + ";\n    peer-as " + ASID + ";\n\n\n    process announce-routes {\n        run /usr/bin/python " + directorioArchivos + "/announce-routes-from_" + str(indice) + ".py;\n    }\n}")
			archivoConf.close()

			#Archivo announce-routes-from_as.py
			archivoAnnounce = open('announce-routes-from_' + str(indice) + '.py','w')
			archivoAnnounce.write("#!/usr/bin/env python\n\nimport sys\nimport time\nimport os\nuserhome=os.path.expanduser('~')\ndesktop = userhome + '/Escritorio/TrazasBGP/'\narchivo=open(desktop + 'PruebaRRC" + str(indice) + "')\n\nipv4 = '172.16." + str(indice) + ".2'\nipv6 = 'fc00::" + str(hex(indice).split('x')[1]) + ":9'\nAS = '" + str(routersInyectoresAS[indice - 1]) + "'\n\ntime.sleep(2)\n\nlinea=archivo.readline()\nanterior=0\n\nwhile linea != '':\n    datos = linea.split('|')\n    actual = datos[1].split(' ')[1]\n    actual = int(actual.split(':')[2])\n    if (actual < anterior ):\n        intervalo= 60 + actual - anterior\n    else :\n        intervalo= actual - anterior\n    anterior = actual\n    time.sleep(intervalo)\n    if datos[2] == \"A\":\n        if (\":\" in datos[5]):\n            message = \"announce route \" + datos[5] + \" next-hop \" + ipv6 + \" as-path [\" + AS + \" \" + datos[6] + \"]\"\n        else:\n            message = \"announce route \" + datos[5] + \" next-hop \" + ipv4 + \" as-path [\" + AS + \" \" + datos[6] + \"]\"\n        sys.stdout.write( message + '\\n')\n        sys.stdout.flush()\n    else:\n        if datos[2] == \"W\":\n            aux = datos[5].split('\\n')\n            if (\":\" in aux[0]):\n                message = \"withdraw route \" + aux[0] + \" next-hop \" + ipv6\n            else:\n                message = \"withdraw route \" + aux[0] + \" next-hop \" + ipv4\n            sys.stdout.write( message + '\\n')\n            sys.stdout.flush()\n    linea=archivo.readline()\n\narchivo.close()\n\nwhile True:\n    time.sleep(1)")
			archivoAnnounce.close()
			os.chmod(dirActual + '/MiniNeXt_' + nombre[0] + '/announce-routes-from_' + str(indice) + '.py', 0755)
			k = k + 1


		############################
		# Agrego IPv6 en los routers

		os.chdir(dirActual)
		archivo = open(sys.argv[1],'r')

		linea = archivo.readline()
		while not ("<bgp>" in linea):
			linea = archivo.readline()

		while not ("<routers>" in linea):
			linea = archivo.readline()

		linea = archivo.readline()
		while not ("</routers>" in linea):
	
			lineaAuxiliar = linea

			auxSplit = linea.split("\"")
			router = '_'.join(auxSplit[1].split())

			# Abro el archivo bgpd.conf para agregarle los neighbors IPv6
			os.chdir(dirNuevo + '/' + dirConfigs + "/" + router)
			archivoNuevo = open('bgpd.conf','a')
			
			archivoNuevo.write("!\n address-family ipv6\n")

			while not ("<neighbors>" in linea):
				linea = archivo.readline()

			linea = archivo.readline()

			if ("reflector=\"true\"" in lineaAuxiliar):
				i = 1
				while not ("</neighbors>" in linea):
					varAux = linea

					linea = linea.split("\"")
					# Le sumo 1 a la IP del neighbor, ya que asi lo agregamos anteriormente
					IPneighbor = linea[3]			
					aux = IPneighbor.split(".")
					aux[3]=int(aux[3])+1
					IPneighbor = aux[0]+"."+aux[1]+"."+aux[2]+"."+str(aux[3])
					
					if ("reflector-client=\"true\"" in varAux):
						archivoNuevo.write(" neighbor " + IPneighbor + " activate\n")

					else:
						archivoNuevo.write(" neighbor " + IPneighbor + " activate\n neighbor " + IPneighbor + " route-reflector-client\n")
			
					i = i + 1
					linea = archivo.readline()
			else:
				while not ("</neighbors>" in linea):
					linea = linea.split("\"")
					# Le sumo 1 a la IP del neighbor, ya que asi lo agregamos anteriormente
					IPneighbor = linea[3]			
					aux = IPneighbor.split(".")
					aux[3]=int(aux[3])+1
					IPneighbor = aux[0]+"."+aux[1]+"."+aux[2]+"."+str(aux[3])
					archivoNuevo.write(" neighbor " + IPneighbor + " activate\n")
					linea = archivo.readline()
		
			# Si tiene un router inyector de peer, lo agrego como neighbor IPv6
			k = 0
			while (k < cantRoutersInyectores) :
				if (routersInyectores[k] == router):
					inyector = k
					inyector = inyector + 1
					archivoNuevo.write(" neighbor 172.16." + str(inyector) + ".2 activate\n")
				k = k + 1	

			archivoNuevo.write(" exit-address-family\n!")			

			archivoNuevo.close()
			os.chdir(dirActual)

			while not ("</router>" in linea):
				linea = archivo.readline()
			linea = archivo.readline()

		archivo.close()
		##########################

		# COSTOS DE LOS LINKS

		if (len(sys.argv) > 2) and (tengoDatos==False):
			os.chdir(dirActual)
			if os.path.isfile(sys.argv[2]):

				archivoCostos = open(sys.argv[2], 'r')

				for r in range(len(routersLinks)):
					routersLinks[r] = 0

				# Leo los costos de los links
				linea = archivoCostos.readline()
				while linea:
					linea = linea.split("\n")
					linea = linea[0].split(" ")
					# Obtengo el costo del enlace
					costo = linea[1]
					linea = linea[0].split("-")
					# Obtengo los routers del enlace
					r1 = linea[0]
					r2 = linea[1]
					
					# Modifico el archivo ospfd.conf del router r1
					os.chdir(dirNuevo+ '/' + dirConfigs + "/" + r1)
					f = open("ospfd.conf", "r")
					contents = f.readlines()
					f.close()

					auxiliarR1 = routersName.index(r1)
					interfazR1 = routersLinks[auxiliarR1]
					auxR1 = "interface " + str(r1) +"-eth" + str(interfazR1) + "\n" 
					indexR1 = contents.index(auxR1)
					contents.insert(indexR1 + 1, " ip ospf cost " + str(costo) + "\n")

					routersLinks[auxiliarR1] = routersLinks[auxiliarR1] + 1

					f = open("ospfd.conf", "w")
					contents = "".join(contents)
					f.write(contents)
					f.close()

					os.chdir(dirActual)

					# Modifico el archivo ospfd.conf del router r2
					os.chdir(dirNuevo+ '/' + dirConfigs + "/" + r2)
					f = open("ospfd.conf", "r")
					contents = f.readlines()
					f.close()

					auxiliarR2 = routersName.index(r2)
					interfazR2 = routersLinks[auxiliarR2]
					auxR2 = "interface " + str(r2) + "-eth" + str(interfazR2) + "\n" 
					indexR2 = contents.index(auxR2)
					contents.insert(indexR2 + 1, " ip ospf cost " + str(costo) + "\n")

					routersLinks[auxiliarR2] = routersLinks[auxiliarR2] + 1

					f = open("ospfd.conf", "w")
					contents = "".join(contents)
					f.write(contents)
					f.close()

					os.chdir(dirActual)
					
					linea= archivoCostos.readline()

				archivoCostos.close()
			
			else:
				print "No existe el archivo de costos en el directorio actual"
		else:
			print "Costos de los enlaces por defecto"
		
		#############################

	else:
		print "El archivo no existe en el directorio actual"
else:
	print "Debes indicar el nombre del archivo"


