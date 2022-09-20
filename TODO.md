# README.md
Create a high-level intro
Create user manual for flagship (least technical. covers how to use a project)
Create admin manual for flagship (covers how to compose a project. include a cookbook.)
Create developer docs for flagship
Create developer docs for io
Create developer docs for inode
Create developer docs for inode.parser
Create developer docs for flagship core
Create developer docs for flagship instructions

# Input/Output:
### OutputStreamFactory
- optOutputStream(fileNameAndPath: String): Option[OutputStream]
- optOutputStream(fileNameAndPath: String, hint: String): Option[OutputStream]
- optOutputStream(fileNameAndPath: String[]): Option[OutputStream]
- optOutputStream(fileNameAndPath: String[], hint: String): Option[OutputStream]

### UrlFactory
- optUrl(fileNameAndPath: String): Option [URL]
- optUrl(fileNameAndPath: String, hint: String): Option[URL]
- optUrl(fileNameAndPath: String[]): Option[URL]
- optUrl(fileNameAndPath: String[], hint: String): Option[URL]

### InputStreamFactory
- optInputStream(fileNameAndPath: String): Option[InputStream]
- optInputStream(fileNameAndPath: String, hint: String): Option[InputStream]
- optInputStream(fileNameAndPath: String[]): Option[InputStream]
- optInputStream(fileNameAndPath: String[], hint: String): Option[InputStream]

### FileFactory
- optFile(fileNameAndPath: String): Option[File]
- optFile(fileNameAndPath: String, hint: String): Option[File]
- optFile(fileNameAndPath: String[]): Option[File]
- optFile(fileNameAndPath: String[], hint: String): Option[File]

### InputResourcesFactory : UrlFactory & InputStreamFactory & FileFactory

### OutputResourcesFactory : OutputStreamFactory
At present, only a simple wrapper around OutputStreamFactory

### ResourcesFactory : InputResourcesFactory & OutputResourcesFactory
Implements all the above and delegates to an instance provided to it. Should be able to return a new instance that replaces an implementation.

# INode
Has 'mutators' which either update the underlying data and return the instance (mutable) or return a new instance with different data (immutable)
- asJava: Object
- hasValue: boolean
- hasChildren: boolean
- hasProperties: boolean
- isParent: boolean = hasChildren || hasProperties
- isLeaf: boolean = !isParent

### IValueNode[A]
- asJava: Object = optValue.OrNull
- optValue: Option[A]
- value: A

### IListNode wrapper around an INodeList
- asJava: Object = nodeList.asJava

### IMapNode wrapper around an INodeMap
- asJava: Object = nodeMap.asJava

### INodeList
- asJava: Object = nodes.map(_.asJava).asJava
Looks like a Jackson NodeList, but for INode

### INodeMap
- asJava: Object = nodes.map(_.asJava).asJava
Looks like a Jackson NodeMap, but for INode

### ComplexNode[A] : IValueNode[A], IListNode, IMapNode
- asJava: Object = this

### object Nodes
Contains various apply methods that return an implementation of INode

# INodeParser
- @throws IOException parse(input: InputStream, hint: String): INode
- @throws IOException parse(inputFactory: InputResourcesFactory, fileAndPath: String, hint: String): INode => parse(inputFactory.optInputStream(fileAndPath, hint), hint)
- @throws IOException parse(inputFactory: InputResourcesFactory, fileAndPath: String[], hint: String): INode => parse(inputFactory.optInputStream(fileAndPath, hint), hint)

## IFormattedNodeParser
- @throws IOException parse(input: InputStream): INode

### JsonNodeParser : IFormattedNodeParser
### YamlNodeParser : IFormattedNodeParser
### XmlNodeParser : IFormattedNodeParser
### PropertiesNodeParser : IFormattedNodeParser
### CsvNodeParser : IFormattedNodeParser
There must be a header row, and the output is an INodeList container INodeMap objects where keys are taken from the header

# Instructions
The default node should be scope

### scope
lets users define a set of instructions that get executed in order
may contain a special "define" instruction
- lets users define new values as strings, maps, or lists
- can reference previously defined values
may contain a special "load" instruction which parses an input resource
may contain "instructions" which is a map of instructions to execute. This is optional, but it allows for "define", "load", and "instructions" to be defined separately if desired.
executes all other keys as if they are instructions.

### foreach
lets users loop over either map or list
by default, should define foreach.key (either the index or key name) and foreach.value, but allow for overrides
may contain a "define" key
- lets users define new values as strings, maps, or lists
- can reference previously defined values

### unzip
lets users unzip a zip resource to a destination

### gzip
lets users unzip a gz resource to a destination

### velocity (alias: vel)
lets users pair a node to an Apache Velocity template

### stringtemplate4 (alias: st4)
lets users pair a node to a string template 4 template

The design should allow the implementation to stand alone where commands may be defined to point to the implementation.
