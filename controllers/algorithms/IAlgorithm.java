package controllers.algorithms;

import models.File;

interface IAlgorithm
{
    public String compress(String uncompressed);

    public String decompress(String compressed);
}