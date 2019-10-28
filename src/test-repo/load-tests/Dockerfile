FROM egaillardon/jmeter-plugins
COPY IdentitySvc.jmx /
CMD ["jmeter", "--nongui", "--testfile", "/IdentitySvc.jmx", "--logfile", "/jmeter/result.jtl", "--reportatendofloadtests", "--reportoutputfolder", "report"]
