# Strongly Typed vs. JavaX Validation

This example projects compares two different approaches to validating incoming
data of a system:

1. Using the JavaX Validation standard to validate incoming requests before
they are handled by the application.
2. Implementing a strongly typed code base which does not allow any types to
be initialized with invalid data at any time anywhere in the application.
