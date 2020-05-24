# 🕺 BALLAD

BALLAD is a comparison of supervised machine-learning approaches for discovering new links in the Linked Data cloud. The name BALLAD is the acronym of *BAtch Learners evaLuAtion for link Discovery*.

![BALLAD Workflow](http://tommaso-soru.it/files/misc/ballad-workflow.png)

## Usage

This project is not Mavenized, however it can be run in UNIX-based systems and imported in the Eclipse IDE.

### Quickstart

For a quick demo on the *OAEI Person1* dataset:

	cd BALLAD
	chmod +x launcher
	./launcher

### Custom run

The main class takes the property file name without extension as input. Property files are stored into the `bin/` directory.

	java -Xmx8G -XX:+HeapDumpOnOutOfMemoryError -cp bin/:lib/* org.aksw.simba.ballad.Ballad "oaei-person1"

### Property files

In each property file, `source_file` and `target_file` have to be located into the `dataset/` directory, whereas `mapping_file` into the `mapping/` directory.

* Source and target types are: 0 (string), 1 (numerical), 2 (date) or 3 (skip value).
* Assigning the same number to a source and a target property creates an alignment (0 with 0, 1 with 1, etc).

Example:

	# Required properties
	
	source_name=OAEIperson11
	source_url=http://www.okkam.org/oaie/person11-
	source_file=dataset/person11.nt.csv
	
	target_name=OAEIperson12
	target_url=http://www.okkam.org/oaie/person12-
	target_file=dataset/person12.nt.csv
	
	source_types=0,0,1,0,3,2,0,1
	target_types=0,0,0,1,2,3,0,1
	
	source_property_align=0,1,2,3,4,5,6,7
	target_property_align=0,1,3,2,5,4,6,7
	
	mapping_file=mapping/oaei-person1.csv
	
	# Optional properties
	
	svm_parameter_c=1E+6
	svm_parameter_eps=1E-3

## Cite

* [Paper](https://www.researchgate.net/publication/264081045_A_Comparison_of_Supervised_Learning_Classifiers_for_Link_Discovery)
* [Tech Report](http://mommi84.github.io/BALLAD)

```bib
@inproceedings{Soru2014,
  author = {Soru, Tommaso and {Ngonga Ngomo}, Axel-Cyrille},
  booktitle = {Proceedings of the 10th International Conference on Semantic Systems (SEMANTiCS)},
  title = {A Comparison of Supervised Learning Classifiers for Link Discovery},
  url = {http://dl.acm.org/citation.cfm?id=2660532},
  year = 2014
}
```
