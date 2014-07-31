# BALLAD

BALLAD is the acronym of *Batch Learners Evaluation for Link Discovery*, a comparison of supervised machine-learning approaches for discovering new links in the Linked Data cloud.

## Citing

Tommaso Soru and Axel-Cyrille Ngonga Ngomo, *"A Comparison of Supervised Learning Classifiers for Link Discovery"*, in proceeding of the 10th International Conference on Semantic Systems (SEMANTiCS), 2014 [[PDF](http://svn.aksw.org/papers/2014/SEMANTiCS_ComparisonLD/public.pdf)]

A technical report is also available [here](http://mommi84.github.io/BALLAD).

## Install

	git clone https://github.com/mommi84/BALLAD.git

## Launch

A quick demo on the *OAEI Person1* dataset (UNIX systems)

	cd BALLAD
	chmod +x launcher
	./launcher

The main class takes the property file name without extension as input. Property files are stored into the `bin/` directory.

	java -Xmx8G -XX:+HeapDumpOnOutOfMemoryError -cp bin/:lib/* org.aksw.simba.ballad.Ballad "oaei-person1"

## Property files

In each property file, `source_file` and `target_file` have to be located into the `dataset/` directory, whereas `mapping_file` into the `mapping/` directory.

Source and target types are: 0 (string), 1 (numerical), 2 (date) or 3 (skip value).

Alignment values align properties having the same number (0 with 0, 1 with 1, etc).

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
