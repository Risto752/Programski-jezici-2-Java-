package map;

public class Map {

      private int rows, columns;
      private  Field[][] map;


      public int getRows(){
            return rows;
      }
      public int getColumns(){
            return columns;
      }
      public Field[][] getMap(){ return map; }


      public Map(int rowsOfMap,int columnsOfMap){

            rows = rowsOfMap;
            columns = columnsOfMap;

            map = new Field[rows][columns];

            for(int i = 0 ; i < rows ; i++)
                  for( int j = 0 ; j < columns; j++)
                  {
                        map[i][j] = new Field(i,j);
                  }
      }
}
