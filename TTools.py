#!/usr/bin/python
# -*- coding: iso-8859-1 -*-

# "========================================================================="
# "	           UDELAR - Facultad de Ingeniería				"
# "		     Proyecto de Grado - Año 2016				"
# "		Autores: Viviana Solla & Gabriel Jambrina			"
# "========================================================================="

import Tkinter,tkFileDialog,ttk
import os
import sys
import ttkcalendar

class simpleapp_tk(Tkinter.Tk):
    def __init__(self,parent):
        Tkinter.Tk.__init__(self,parent)
        self.parent = parent
        self.initialize()

    def initialize(self):
        self.grid()
	self.eval('tk::PlaceWindow %s center' % self.winfo_pathname(self.winfo_id()))

        button1 = Tkinter.Button(self,text="Convertir archvio GML a XML",font=('times', 14, 'bold'), command=self.OnButtonClickB1)
        button1.grid(column=0,row=1,columnspan=1,sticky='EW')
	button2 = Tkinter.Button(self,text="Preparar emulación de Red", font=('times', 14, 'bold') , command=self.OnButtonClickB2)
        button2.grid(column=0,row=2,columnspan=1,sticky='EW')
	button3 = Tkinter.Button(self,text="Cargar Base de Datos Mysql",font=('times', 14, 'bold'), command=self.OnButtonClickB3)
        button3.grid(column=0,row=3,columnspan=1,sticky='EW')
	button4 = Tkinter.Button(self,text="Borrar archivos generados por bgpdump",font=('times', 14, 'bold'), command=self.OnButtonClickB4)
        button4.grid(column=0,row=4,columnspan=1,sticky='EW')
	button5 = Tkinter.Button(self,text="Ejecutar MiniNExt",font=('times', 14, 'bold'), command=self.OnButtonClickB5)
        button5.grid(column=0,row=5,columnspan=1,sticky='EW')
	button6 = Tkinter.Button(self,text="Descargar trazas BGP",font=('times', 14, 'bold'), command=self.OnButtonClickB6)
        button6.grid(column=0,row=6,columnspan=1,sticky='EW')
	self.image = Tkinter.PhotoImage(file="img/exit.png")
	button6 = Tkinter.Button(self, text="SALIR",font=('times', 14, 'bold'), image=self.image, anchor=Tkinter.SE, compound="right", command=quit)
        button6.grid(column=0,row=7, sticky='EW')

        self.labelVariable = Tkinter.StringVar()
        self.label = Tkinter.Label(self,textvariable=self.labelVariable,anchor="w",fg="white",bg="green")
        self.label.grid(column=0,row=10,columnspan=2,sticky='EW')
        self.labelVariable.set("                                TTools v1.0")

        self.grid_columnconfigure(0,weight=1)
        self.resizable(True,False)
##################BOTON B1###########################	
    def OnButtonClickB1(self):
        self.labelVariable.set(" Convertidor de GML a XML" )
	file = tkFileDialog.askopenfile(parent=self,mode='rb',title='Selecciona el archivo a convertir')
	if file != None:
	   self.root = Tkinter.Tk()
	   self.root.eval('tk::PlaceWindow %s center' % self.root.winfo_pathname(self.root.winfo_id()))
	   self.root.title("Numero de Sistema Autónomo")
		   
	   print "¡File OK!"
	   self.abs_path = os.path.abspath(file.name)
	   #dirActual = os.getcwd()
      	   Tkinter.Label(self.root, text="Ingrese el número de AS", font=('arial', 12, 'bold'), width=30).pack(pady=10)  
           self.e = Tkinter.Entry(self.root, width=10)
           self.e.pack(pady=10)
           b = Tkinter.Button(self.root, text="OK",font=('times', 12, 'bold'), command=self.onClickB1)
           b.pack(pady=20)
     	   	   
    def onClickB1(self):
	os.system("python GMLtoXMLconverter.py "+self.abs_path+" "+self.e.get())
	print "ASN= ", self.e.get()
        self.root.destroy()
	self.labelVariable.set("Archivo convertido con exito")

##################BOTON B2###########################	
    def OnButtonClickB2(self):
 	self.routersName = []
	self.lstASN = []
        self.labelVariable.set(" Generador de archivos de configuracion" )
	file = tkFileDialog.askopenfile(parent=self,mode='rb',title='Seleccione el archivo XML que representa la topología')
	if file != None:
	   self.root = Tkinter.Tk()
 	   self.root.eval('tk::PlaceWindow %s center' % self.root.winfo_pathname(self.root.winfo_id()))

	   #self.root.resizable(width=False, height=False)
	   self.root.grid()
	   self.root.title("Seleccion routers de borde")
	   
	   print "¡File OK!"
	   self.abs_path = os.path.abspath(file.name) #os.path.basename(file.name)
	   #Leo los routers de AS
	   linea = file.readline()
	   while not ("<topology>" in linea):
		linea = file.readline()
	   while not ("</nodes>" in linea):
		if ("<node id=" in linea):
	    	    auxLinea = linea.split("\"")
		    r = auxLinea[1]
		    r = '_'.join(r.split())
		    if not r in self.routersName:
			self.routersName.append(r)
		linea = file.readline()
	   #Muestro la lista con los routers
	   self.label1 = Tkinter.Label(self.root,text= "Selecciones los routers que mantienen sesiones eBGP",height=2, width=55,font=('arial', 12, 'bold'));self.label1.pack()
	   self.s1 = Tkinter.Scrollbar(self.root)
	   self.s2 = Tkinter.Scrollbar(self.root)
	   self.L1 = Tkinter.Listbox(self.root, height=20, font=('arial', 11))
	   self.L2 = Tkinter.Listbox(self.root, height=20)
  	   self.s1.pack(side=Tkinter.LEFT, fill=Tkinter.Y)
	   self.s2.pack(side=Tkinter.RIGHT, fill=Tkinter.Y)
	   self.L1.pack(side=Tkinter.LEFT, fill=Tkinter.Y)
	   self.L2.pack(side=Tkinter.RIGHT, fill=Tkinter.Y)
	   self.s1.config(command=self.L1.yview)
	   self.s2.config(command=self.L2.yview)
	   self.L1.config(yscrollcommand=self.s1.set)
	   self.L2.config(yscrollcommand=self.s2.set)

	   for i in self.routersName: 
		self.L1.insert(Tkinter.END, i)
	   self.L1.select_set(0)
	   self.b3 = Tkinter.Button(self.root, text="Seleccionar =>", command=self.onClickB3, height=2, width=10, bg="green", font=('arial', 12));self.b3.pack()
	   self.b4 = Tkinter.Button(self.root, text="<= Quitar", command=self.onClickB4, height=2, width=10, bg="red", font=('arial', 12));self.b4.pack()
           self.b2 = Tkinter.Button(self.root, text="Siguiente", command=self.onClickB2, height=2, width=10, font=('times', 12));self.b2.pack(side="bottom")

    def onClickB3(self):
	index = int(self.L1.curselection()[0])
	self.L2.insert(Tkinter.END, self.routersName[index])
	self.L2.select_set(self.L2.size()-1)

    def onClickB4(self):
	index = self.L2.curselection()[0]
	self.L2.delete(index)
	self.L2.select_set(self.L2.size()-1)

    def onClickB2(self):
	self.L1.pack_forget();self.s1.pack_forget();self.L2.pack_forget();self.s2.pack_forget();self.b2.pack_forget();self.b3.pack_forget();
	self.b4.pack_forget();self.label1.pack_forget();

	self.root.title("ASN de Vecinos eBGP")
	self.label2 = Tkinter.Label(self.root,height=2,width=30,font=('arial', 15, 'bold'),text=self.L2.get(0, Tkinter.END)[0],anchor=Tkinter.CENTER);self.label2.pack()
	self.e = Tkinter.Entry(self.root, font=("Calibri",12),justify="center",width=8,bg="#1E6FBA")
	self.asn=65000
	self.e.insert(Tkinter.END, self.asn);	self.e.pack()
	self.index=1;
	self.b5 = Tkinter.Button(self.root, height=1, width=8, text="Siguiente", command=self.onClickB5, font=('arial', 12));self.b5.pack(pady=10)
    
    def onClickB5(self):	
        self.asn+=10
	self.lstASN.insert(self.index-1,self.e.get())
	self.e.delete(0, Tkinter.END);        self.e.insert(Tkinter.END, self.asn) 
        self.label2.config(text=self.L2.get(0, Tkinter.END)[self.index] )
        self.index+=1
	if (self.L2.size() == self.index):
	    self.b5.pack_forget()
	    self.b6 = Tkinter.Button(self.root, height=1, width=8, text="Terminar", command=self.onClickB6, font=('arial', 12)); self.b6.pack(pady=10)
	    
	    
    def onClickB6(self):
	self.lstASN.insert(self.index-1,self.e.get())
	comando="python emulGen.py "+self.abs_path+" "
	counter=0
        for i in self.L2.get(0, Tkinter.END):
	    counter+=1
	    if (counter==self.index):
		comando+=i+" "
	    else:
		comando+=i+","
	counter=0
        for i in self.lstASN :
    	   counter+=1
	   if (counter==self.index):
		comando+=i+" "
	   else:    	    
		comando+=i+","
	counter=0;
        os.system(comando)
        #print comando
	self.root.destroy()
	self.labelVariable.set("                                Topology Tools v1.0")

#####################BOTON B3#########################
    def OnButtonClickB3(self):
        self.labelVariable.set("Cargar bases de datos Mysql" )
	self.directory = tkFileDialog.askdirectory(parent=self,title='Seleccione la ubicacion de la carpeta mininext')
	if self.directory != "":
	   self.root = Tkinter.Tk()
 	   self.root.eval('tk::PlaceWindow %s center' % self.root.winfo_pathname(self.root.winfo_id()))
	   self.root.title("Nobre de la base de datos") 
	   Tkinter.Label(self.root, height=2,width=40,font=('arial', 11, 'bold'), text="Ingresee el nombre de la base").pack()  
           self.baseName = Tkinter.Entry(self.root, width=20)
           self.baseName.pack(padx=50)
	   Tkinter.Label(self.root, height=2,width=30,font=('arial', 11, 'bold'), text="Selecione el algoritmo utilizado").pack() 
	   self.L3 = Tkinter.Listbox(self.root, height=10)
	   self.L3.pack()
	   self.algorithm=["FM","RR_Sep", "RR_SepD", "RR_SepS", "RR_Bates",  "RR_BatesY", "RR_BatesZ", "RR_Zhang"]
	   self.L3.insert(Tkinter.END, self.algorithm[0]);self.L3.insert(Tkinter.END, self.algorithm[1]);self.L3.insert(Tkinter.END, self.algorithm[2]);
	   self.L3.insert(Tkinter.END, self.algorithm[3]); self.L3.insert(Tkinter.END, self.algorithm[4]);self.L3.insert(Tkinter.END, self.algorithm[5]);
	   self.L3.insert(Tkinter.END, self.algorithm[6]); self.L3.insert(Tkinter.END, self.algorithm[7])
	   self.L3.select_set(0)
           Tkinter.Button(self.root, text="Cargar BD", command=self.onClickB7, font=('arial', 12)).pack()

    def onClickB7(self):
	if self.baseName.get() != "" :
	   print "Cargar base"
	   print "python loadDB.py "+self.directory+" "+self.baseName.get() +" "+ self.algorithm[self.L3.curselection()[0]]
	   os.system("python loadDB.py "+self.directory+" "+self.baseName.get() +" "+ self.algorithm[self.L3.curselection()[0]])
   	   self.root.destroy()
	   self.labelVariable.set("                                Topology Tools v1.0")
	else:
	   print "WARNING: Falto completar un campo"	   
	
#####################BOTON B4#########################
    def OnButtonClickB4(self):
        self.labelVariable.set("Borrar archivos temporales generados por tcpdump" )
	self.directory = tkFileDialog.askdirectory(parent=self,title='Seleccione la ubicacion de la carpeta mininext')
	if self.directory != "":
	   print "Borrando archivos temporales de la carpeta "+ self.directory
	   os.system("python deleteTemporaryFiles.py "+self.directory)
	self.labelVariable.set("                                Topology Tools v1.0")


#####################BOTON B5#########################
    def OnButtonClickB5(self):
        self.labelVariable.set("Empezar emulación con MiniNExt" )
	self.directory = tkFileDialog.askdirectory(parent=self,title='Seleccione la ubicacion de la carpeta generada')
	if self.directory != "":
	   print "Running sudo python "+ self.directory + "/start.py"
	   os.system("sudo python "+ self.directory + "/start.py")
#####################BOTON B6#########################
    def OnButtonClickB6(self):
        self.labelVariable.set("Descargar Trazas BGP desde www.ripe.net" )
	self.directory = tkFileDialog.askdirectory(parent=self,title='Seleccione la ubicacion donde descargar la traza')
	if self.directory != "":
	   self.root = Tkinter.Tk()
 	   self.root.eval('tk::PlaceWindow %s center' % self.root.winfo_pathname(self.root.winfo_id()))
	   self.root.title("TRAZA BGP")
	   Tkinter.Label(self.root, height=2,width=40,font=('arial', 11, 'bold'), text="Seleccione el origen de la traza").pack()
	   self.opM = ttk.Combobox(self.root, width=10, values=[ "rrc00" ,"rrc01", "rrc02", "rrc03","rrc04","rrc05","rrc06","rrc07","rrc08","rrc09","rrc10","rrc11","rrc12","rrc13","rrc14","rrc15","rrc16"])
	   self.opM.current(0)
	   self.opM.pack()
	   
	   Tkinter.Label(self.root, height=2,width=40,font=('arial', 11, 'bold'), text="Seleccione el día").pack()
	   self.calendar = ttkcalendar.Calendar(self.root)
           self.calendar.pack()
	   
	   Tkinter.Label(self.root, height=2,width=40,font=('arial', 11, 'bold'), text="Seleccione la hora").pack()
	   lstHours=[]
	   cont=0;
	   for i in range(0,24):
	       for j in range(0,12):
		   if (j <= 1):
			valor=str(0)+str(j*5)
		   else:
			valor=str(j*5)
		   valor=str(i)+":"+valor
		   if (i <= 9):
			valor=str(0)+valor
	           lstHours.insert(cont,valor)
		   cont+=1
	   Tkinter.Label(self.root, height=2,width=40,font=('arial', 11), text="Hora Inicio").pack()
 	   self.opHourMin = ttk.Combobox(self.root, width=5,values=lstHours)
	   self.opHourMin.current(0)
	   self.opHourMin.pack()
	
	   Tkinter.Label(self.root, height=2,width=40,font=('arial', 11), text="Hora Fin").pack()
	   self.opHourMax = ttk.Combobox(self.root, width=5,values=lstHours)
	   self.opHourMax.current(0)
	   self.opHourMax.pack()
	   buttonCalendar = Tkinter.Button(self.root, text="Aceptar", command=self.onClickCalendar).pack(side=Tkinter.RIGHT)
	   buttonCancel = Tkinter.Button(self.root, text="Cancelar", command=self.root.destroy).pack(side=Tkinter.LEFT)
	     
    def onClickCalendar(self):
	   date= str(self.calendar.selection).split(" ")[0]
	   #Ej: python downloadFromRipe.py rrc00 2014-02-15 16:45 17:10 /home/
	   print "python downloadFromRipe.py "+self.opM.get()+" "+date+" "+self.opHourMin.get()+" "+self.opHourMax.get()+" "+self.directory
	   os.system("python downloadFromRipe.py "+self.opM.get()+" "+date+" "+self.opHourMin.get()+" "+self.opHourMax.get()+" "+self.directory)
	   self.root.eval('::ttk::CancelRepeat')
	   self.root.destroy()
	   self.labelVariable.set("                                Topology Tools v1.0")

if __name__ == "__main__":
    app = simpleapp_tk(None)
    app.title('Topology Tools')
    app.mainloop()
