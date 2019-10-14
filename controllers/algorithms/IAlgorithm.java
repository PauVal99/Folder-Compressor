package controllers.algorithms;

import models.File;

interface IAlgorithm
{
    void compress(File f);

    void decompress(File f);
}