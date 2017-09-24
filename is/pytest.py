import xmlrpc.client

with xmlrpc.client.ServerProxy("http://localhost:8080/") as proxy:
	proxy.callSync(TestPiSlave.PIFUNCTION,2000)




