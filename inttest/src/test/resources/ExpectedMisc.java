package com.iksgmbh.moglicc.demo;

<<<<<<< HEAD
=======
import org.joda.time.DateTime;
>>>>>>> 656c84c58ad794ed34c58c30ecc9bf656c921412
import java.lang.Boolean;
import java.util.List;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.lang.Float;
import java.lang.Double;
import java.util.HashSet;
import java.lang.Character;
import java.math.BigDecimal;
<<<<<<< HEAD
import com.iksgmbh.moglicc.demo.Person;
import java.util.Arrays;
=======
import java.util.Arrays;
import com.iksgmbh.moglicc.demo.Person;
>>>>>>> 656c84c58ad794ed34c58c30ecc9bf656c921412
import java.lang.Byte;

/**
* JavaBean class of the MOGLiCC JavaBean Group
*
* @author generated by MOGLiCC
*/
public class Misc extends Person
{
	// instance fields
	private String text;
	private boolean ready;
	private char primitivChar;
	private byte numberByte;
	private int numberInt;
	private long numberLong;
	private float numberFloat;
	private double numberDouble;
	private Boolean booleanWrapper;
	private Character charWrapper;
	private Byte byteWrapper;
	private Integer intWrapper;
	private Long longWrapper;
	private Float floatWrapper;
	private Double doubleWrapper;
	private BigDecimal bigDecimal;
<<<<<<< HEAD
=======
	private DateTime dateTime;
>>>>>>> 656c84c58ad794ed34c58c30ecc9bf656c921412
	private List<Long> listOfLongs;
	private List<String> stringList;
	private String[] stringArray;
	private HashSet<String> hashSet;
	private Person instanceVariable;

	// ===============  setter methods  ===============

	public void setText(final String text)
	{
		this.text = text;
	}

	public void setReady(final boolean ready)
	{
		this.ready = ready;
	}

	public void setPrimitivChar(final char primitivChar)
	{
		this.primitivChar = primitivChar;
	}

	public void setNumberByte(final byte numberByte)
	{
		this.numberByte = numberByte;
	}

	public void setNumberInt(final int numberInt)
	{
		this.numberInt = numberInt;
	}

	public void setNumberLong(final long numberLong)
	{
		this.numberLong = numberLong;
	}

	public void setNumberFloat(final float numberFloat)
	{
		this.numberFloat = numberFloat;
	}

	public void setNumberDouble(final double numberDouble)
	{
		this.numberDouble = numberDouble;
	}

	public void setBooleanWrapper(final Boolean booleanWrapper)
	{
		this.booleanWrapper = booleanWrapper;
	}

	public void setCharWrapper(final Character charWrapper)
	{
		this.charWrapper = charWrapper;
	}

	public void setByteWrapper(final Byte byteWrapper)
	{
		this.byteWrapper = byteWrapper;
	}

	public void setIntWrapper(final Integer intWrapper)
	{
		this.intWrapper = intWrapper;
<<<<<<< HEAD
	}

	public void setLongWrapper(final Long longWrapper)
	{
		this.longWrapper = longWrapper;
	}

	public void setFloatWrapper(final Float floatWrapper)
	{
		this.floatWrapper = floatWrapper;
	}

	public void setDoubleWrapper(final Double doubleWrapper)
	{
		this.doubleWrapper = doubleWrapper;
	}

	public void setBigDecimal(final BigDecimal bigDecimal)
	{
		this.bigDecimal = bigDecimal;
	}

	public void setListOfLongs(final List<Long> listOfLongs)
	{
		this.listOfLongs = listOfLongs;
	}

	public void setStringList(final List<String> stringList)
	{
=======
	}

	public void setLongWrapper(final Long longWrapper)
	{
		this.longWrapper = longWrapper;
	}

	public void setFloatWrapper(final Float floatWrapper)
	{
		this.floatWrapper = floatWrapper;
	}

	public void setDoubleWrapper(final Double doubleWrapper)
	{
		this.doubleWrapper = doubleWrapper;
	}

	public void setBigDecimal(final BigDecimal bigDecimal)
	{
		this.bigDecimal = bigDecimal;
	}

	public void setDateTime(final DateTime dateTime)
	{
		this.dateTime = dateTime;
	}

	public void setListOfLongs(final List<Long> listOfLongs)
	{
		this.listOfLongs = listOfLongs;
	}

	public void setStringList(final List<String> stringList)
	{
>>>>>>> 656c84c58ad794ed34c58c30ecc9bf656c921412
		this.stringList = stringList;
	}

	public void setStringArray(final String[] stringArray)
	{
		this.stringArray = stringArray;
	}

	public void setHashSet(final HashSet<String> hashSet)
	{
		this.hashSet = hashSet;
	}

	public void setInstanceVariable(final Person instanceVariable)
	{
		this.instanceVariable = instanceVariable;
	}

	// ===============  getter methods  ===============

	public String getText()
	{
		return text;
	}

	public boolean getReady()
	{
		return ready;
	}

	public char getPrimitivChar()
	{
		return primitivChar;
	}

	public byte getNumberByte()
	{
		return numberByte;
	}

	public int getNumberInt()
	{
		return numberInt;
	}

	public long getNumberLong()
	{
		return numberLong;
	}

	public float getNumberFloat()
	{
		return numberFloat;
	}

	public double getNumberDouble()
	{
		return numberDouble;
	}

	public Boolean getBooleanWrapper()
	{
		return booleanWrapper;
	}

	public Character getCharWrapper()
	{
		return charWrapper;
	}

	public Byte getByteWrapper()
	{
		return byteWrapper;
	}

	public Integer getIntWrapper()
	{
		return intWrapper;
	}

	public Long getLongWrapper()
	{
		return longWrapper;
	}

	public Float getFloatWrapper()
	{
		return floatWrapper;
	}

	public Double getDoubleWrapper()
	{
		return doubleWrapper;
	}

	public BigDecimal getBigDecimal()
	{
		return bigDecimal;
	}

<<<<<<< HEAD
=======
	public DateTime getDateTime()
	{
		return dateTime;
	}

>>>>>>> 656c84c58ad794ed34c58c30ecc9bf656c921412
	public List<Long> getListOfLongs()
	{
		return listOfLongs;
	}

	public List<String> getStringList()
	{
		return stringList;
	}

	public String[] getStringArray()
	{
		return stringArray;
	}

	public HashSet<String> getHashSet()
	{
		return hashSet;
	}

	public Person getInstanceVariable()
	{
		return instanceVariable;
	}

	// ===============  additional Javabean methods  ===============

	@Override
	public String toString()
	{
		return "Misc ["
				+ "text = " + text + ", "
				+ "ready = " + ready + ", "
				+ "primitivChar = " + primitivChar + ", "
				+ "numberByte = " + numberByte + ", "
				+ "numberInt = " + numberInt + ", "
				+ "numberLong = " + numberLong + ", "
				+ "numberFloat = " + numberFloat + ", "
				+ "numberDouble = " + numberDouble + ", "
				+ "booleanWrapper = " + booleanWrapper + ", "
				+ "charWrapper = " + charWrapper + ", "
				+ "byteWrapper = " + byteWrapper + ", "
				+ "intWrapper = " + intWrapper + ", "
				+ "longWrapper = " + longWrapper + ", "
				+ "floatWrapper = " + floatWrapper + ", "
				+ "doubleWrapper = " + doubleWrapper + ", "
				+ "bigDecimal = " + bigDecimal + ", "
<<<<<<< HEAD
=======
				+ "dateTime = " + dateTime + ", "
>>>>>>> 656c84c58ad794ed34c58c30ecc9bf656c921412
				+ "listOfLongs = " + listOfLongs + ", "
				+ "stringList = " + stringList + ", "
				+ "stringArray = " + Arrays.toString(stringArray) + ", "
				+ "hashSet = " + hashSet + ", "
				+ "instanceVariable = " + instanceVariable + ""
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		final Misc other = (Misc) obj;

		if (text == null)
		{
			if (other.text != null)
				return false;
		} else
		{
			if (! text.equals(other.text))
				   return false;
		}
		if (ready != other.ready)
			return false;
		if (primitivChar != other.primitivChar)
			return false;
		if (numberByte != other.numberByte)
			return false;
		if (numberInt != other.numberInt)
			return false;
		if (numberLong != other.numberLong)
			return false;
		if (Float.floatToIntBits(numberFloat) != Float.floatToIntBits(other.numberFloat))
			return false;
		if (Double.doubleToLongBits(numberDouble) != Double.doubleToLongBits(other.numberDouble))
			return false;
		if (booleanWrapper == null)
		{
			if (other.booleanWrapper != null)
				return false;
		} else
		{
			if (! booleanWrapper.equals(other.booleanWrapper))
				   return false;
		}
		if (charWrapper == null)
		{
			if (other.charWrapper != null)
				return false;
		} else
		{
			if (! charWrapper.equals(other.charWrapper))
				   return false;
		}
		if (byteWrapper == null)
		{
			if (other.byteWrapper != null)
				return false;
		} else
		{
			if (! byteWrapper.equals(other.byteWrapper))
				   return false;
		}
		if (intWrapper == null)
		{
			if (other.intWrapper != null)
				return false;
		} else
		{
			if (! intWrapper.equals(other.intWrapper))
				   return false;
		}
		if (longWrapper == null)
		{
			if (other.longWrapper != null)
				return false;
		} else
		{
			if (! longWrapper.equals(other.longWrapper))
				   return false;
		}
		if (floatWrapper == null)
		{
			if (other.floatWrapper != null)
				return false;
		} else
		{
			if (! floatWrapper.equals(other.floatWrapper))
				   return false;
		}
		if (doubleWrapper == null)
		{
			if (other.doubleWrapper != null)
				return false;
		} else
		{
			if (! doubleWrapper.equals(other.doubleWrapper))
				   return false;
		}
		if (bigDecimal == null)
		{
			if (other.bigDecimal != null)
				return false;
		} else
		{
			if (! bigDecimal.equals(other.bigDecimal))
				   return false;
		}
<<<<<<< HEAD
=======
		if (dateTime == null)
		{
			if (other.dateTime != null)
				return false;
		} else
		{
			if (! dateTime.equals(other.dateTime))
				   return false;
		}
>>>>>>> 656c84c58ad794ed34c58c30ecc9bf656c921412
		if (listOfLongs == null)
		{
			if (other.listOfLongs != null)
				return false;
		} else
		{
			if (! listOfLongs.equals(other.listOfLongs))
				   return false;
		}
		if (stringList == null)
		{
			if (other.stringList != null)
				return false;
		} else
		{
			if (! stringList.equals(other.stringList))
				   return false;
		}
		if (!Arrays.equals(stringArray, other.stringArray))
			return false;
		if (hashSet == null)
		{
			if (other.hashSet != null)
				return false;
		} else
		{
			if (! hashSet.equals(other.hashSet))
				   return false;
		}
		if (instanceVariable == null)
		{
			if (other.instanceVariable != null)
				return false;
		} else
		{
			if (! instanceVariable.equals(other.instanceVariable))
				   return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

<<<<<<< HEAD
               result = prime * result + ((text == null) ? 0 : 3556653);
=======
                result = prime * result + ((text == null) ? 0 : 3556653);
>>>>>>> 656c84c58ad794ed34c58c30ecc9bf656c921412
 		result = prime * result + (ready ? 1231 : 1237);
		result = prime * result + primitivChar;
		result = prime * result + numberByte;
		result = prime * result + numberInt;
 		result = prime * result + (int) (numberLong ^ (numberLong >>> 32));
 		result = prime * result + Float.floatToIntBits(numberFloat);
		long temp = Double.doubleToLongBits(numberDouble);
		result = prime * result + (int) (temp ^ (temp >>> 32));
<<<<<<< HEAD
               result = prime * result + ((booleanWrapper == null) ? 0 : -63687861);
               result = prime * result + ((charWrapper == null) ? 0 : 80559197);
               result = prime * result + ((byteWrapper == null) ? 0 : 1668153963);
               result = prime * result + ((intWrapper == null) ? 0 : -1142847068);
               result = prime * result + ((longWrapper == null) ? 0 : -5877129);
               result = prime * result + ((floatWrapper == null) ? 0 : -815539561);
               result = prime * result + ((doubleWrapper == null) ? 0 : 1558176642);
               result = prime * result + ((bigDecimal == null) ? 0 : -554856911);
               result = prime * result + ((listOfLongs == null) ? 0 : -1260473502);
               result = prime * result + ((stringList == null) ? 0 : -1573317553);
		result = prime * result + Arrays.hashCode(stringArray);
               result = prime * result + ((hashSet == null) ? 0 : 697516148);
               result = prime * result + ((instanceVariable == null) ? 0 : 1081501233);
=======
                result = prime * result + ((booleanWrapper == null) ? 0 : -63687861);
                result = prime * result + ((charWrapper == null) ? 0 : 80559197);
                result = prime * result + ((byteWrapper == null) ? 0 : 1668153963);
                result = prime * result + ((intWrapper == null) ? 0 : -1142847068);
                result = prime * result + ((longWrapper == null) ? 0 : -5877129);
                result = prime * result + ((floatWrapper == null) ? 0 : -815539561);
                result = prime * result + ((doubleWrapper == null) ? 0 : 1558176642);
                result = prime * result + ((bigDecimal == null) ? 0 : -554856911);
                result = prime * result + ((dateTime == null) ? 0 : 1792749467);
                result = prime * result + ((listOfLongs == null) ? 0 : -1260473502);
                result = prime * result + ((stringList == null) ? 0 : -1573317553);
				 result = prime * result + Arrays.hashCode(stringArray);
                result = prime * result + ((hashSet == null) ? 0 : 697516148);
                result = prime * result + ((instanceVariable == null) ? 0 : 1081501233);
>>>>>>> 656c84c58ad794ed34c58c30ecc9bf656c921412

		return result;
	}

}
